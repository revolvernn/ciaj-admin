package com.ciaj.boot.component.aspect;

import com.alibaba.fastjson.JSON;
import com.ciaj.base.AbstractBase;
import com.ciaj.boot.component.config.shiro.ShiroUser;
import com.ciaj.boot.modules.sys.entity.po.SysLogPo;
import com.ciaj.boot.modules.sys.service.SysLogService;
import com.ciaj.comm.annotation.OperationLog;
import com.ciaj.comm.constant.LogTypeEnum;
import com.ciaj.comm.utils.CommUtil;
import com.ciaj.comm.utils.ExceptionsUtils;
import com.ciaj.comm.utils.JSONUtils;
import com.ciaj.comm.utils.RequestUtils;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2019/1/10 17:08
 * @Description:
 */
@Aspect  // 使用@Aspect注解声明一个切面
@Component
@Log4j2
public class SysLogAspect {

    @Value("${log.isInsert}")
    private Boolean isInsert;
    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(com.ciaj.comm.annotation.OperationLog)")
    public void logPointCut() {
    }

    /**
     * 环绕通知 @Around  ， 当然也可以使用 @Before (前置通知)  @After (后置通知)
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;
        try {
            saveLog(point, time);
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 保存日志
     *
     * @param joinPoint
     * @param time
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            SysLogPo sysLogPo = new SysLogPo();
            sysLogPo.setTime(time);
            OperationLog operationLog = method.getAnnotation(OperationLog.class);
            if (operationLog == null) {
                return;
            }
            //注解上的描述
            sysLogPo.setOperation(operationLog.operation());
            sysLogPo.setDescription(operationLog.content());
            sysLogPo.setType(operationLog.type().name());

            //请求的 类名、方法名
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = signature.getName();
            sysLogPo.setMethod(className + "." + methodName);

            //设置IP地址
            sysLogPo.setIp(RequestUtils.getRemoteAddr());
            //请求的参数
            Object[] args = joinPoint.getArgs();

            if (args != null && args.length > 0) {
                if (args[0] instanceof Throwable) {
                    Throwable e = (Throwable) args[0];
                    sysLogPo.setOperation(LogTypeEnum.ERROR.name());
                    sysLogPo.setParams(ExceptionsUtils.getStackTraceAsString(e));
                } else {
                    List<Object> list = getArgsList(args);
                    String params = JSONUtils.obj2json(list);
                    sysLogPo.setParams(JSON.parse(params).toString());
                }
            }
            try {
                //用户名
                ShiroUser currentUser = CommUtil.getLoginUser();
                String username = currentUser.getUsername();
                sysLogPo.setUsername(username);
            } catch (Exception e) {
            }
            if (operationLog.isInsert() && isInsert) {
                sysLogService.insertOrUpdatePre(sysLogPo, AbstractBase.INSERT);
                sysLogService.insert(sysLogPo);
            }
            if (log.isDebugEnabled()) {
                log.debug("===========保存日志：{},{}", isInsert, JSONUtils.obj2json(sysLogPo));
            }
        } catch (Exception e) {
            log.error("保存日志失败", e);
        }
    }

    private List<Object> getArgsList(Object[] args) {
        List<Object> list = new ArrayList();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof HttpServletResponse || arg instanceof HttpServletRequest) {
            } else {
                list.add(arg);
            }
        }
        return list;
    }


    /**
     * 异常通知方法只在连接点方法出现异常后才会执行，否则不执行。在异常通知方法中可以获取连接点方法出现的异常。在切面类中异常通知方法
     * 通过throwing属性指定连接点方法出现异常信息存储在ex变量中，在异常通知方法中就可以从ex变量中获取异常信息了
     *
     * @param point
     * @param e
     */
    @AfterThrowing(pointcut = "logPointCut()", throwing = "e")
    public void afterThrowing(JoinPoint point, Throwable e) {
        String methodName = point.getSignature().getName();
        List<Object> args = getArgsList(point.getArgs());
        log.info("===========afterThrowing连接点方法为：{},参数为：{},异常为：{}", methodName, args, e);
    }
}
