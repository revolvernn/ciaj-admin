package com.ciaj.boot.component.interceptor;

import com.ciaj.comm.utils.JSONUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @Author: Ciaj.
 * @Date: 2019/2/25 14:25
 * @Description:
 */
@Intercepts({
		@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
		@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
		@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
})
@Component
@Log4j2
public class MybatisInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
//		Object target = invocation.getTarget();
//		Object result = null;
//		// 拦截sql
//		Object[] args = invocation.getArgs();
//		MappedStatement statement = (MappedStatement) args[0];
//		Object parameterObject = args[1];
//		BoundSql boundSql = statement.getBoundSql(parameterObject);
//		String sql = boundSql.getSql();
//		//log.info("MybatisInterceptor sql Log sql：{}，param：{}", sql, JSONUtils.obj2json(parameterObject));
//
//		if (target instanceof Executor) {
//			long start = System.currentTimeMillis();
//			result = invocation.proceed();
//			long end = System.currentTimeMillis();
//			log.info("MybatisInterceptor sql TimerInterceptor execute [{}] cost [{}] ms", invocation.getMethod().getName(), (end - start));
//		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object o) {
		return Plugin.wrap(o, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}
}
