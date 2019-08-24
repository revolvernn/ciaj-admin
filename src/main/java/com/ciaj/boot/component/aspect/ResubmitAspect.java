package com.ciaj.boot.component.aspect;

import com.ciaj.boot.component.config.redis.RedisUtil;
import com.ciaj.comm.utils.RequestUtils;
import lombok.extern.log4j.Log4j2;
import net.sf.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Description  重复提交切面
 * @Author Ciaj.
 * @Date 2019/5/10 9:53
 * @Version 1.0
 */
@Aspect  // 使用@Aspect注解声明一个切面
@Component
@Log4j2
public class ResubmitAspect {

	@Autowired
	RedisUtil redisUtil;

	@Around("@annotation(com.ciaj.comm.annotation.Resubmit)")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		//获取注解
		String ip = RequestUtils.getRemoteAddr();
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();

		//目标类、方法
		String className = method.getDeclaringClass().getName();
		String name = method.getName();
		String ipKey = String.format("%s#%s", className, name);
		int hashCode = Math.abs(ipKey.hashCode());
		String key = String.format("resubmitData_%s_%d", ip, hashCode);
		log.info("ipKey={},hashCode={},key={}", ipKey, hashCode, key);
		Object[] args = point.getArgs();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(key, args);

		log.info(jsonObject.toString());

		//TODO

		return point.proceed();
	}
}
