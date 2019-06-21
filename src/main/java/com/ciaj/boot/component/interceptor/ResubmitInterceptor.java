package com.ciaj.boot.component.interceptor;

import com.ciaj.boot.component.filter.ResubmitRequestWrapper;
import com.ciaj.comm.annotation.Resubmit;
import com.ciaj.comm.exception.BsRException;
import com.ciaj.comm.utils.JSONUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Description 表单重复拦截
 * @Author Ciaj.
 * @Date 2019/4/16 16:57
 * @Version 1.0
 */
@Log4j2
public class ResubmitInterceptor extends HandlerInterceptorAdapter {

	private static String RESUBMIT_DATA = "resubmitData";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Resubmit annotation = method.getAnnotation(Resubmit.class);
			if (annotation != null && isResubmit(annotation, request)) throw new BsRException("请勿重复提交");
		}
		return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		HttpSession session = request.getSession(false);
		try {
			if (session != null && session.getAttribute(RESUBMIT_DATA) != null) {
				session.removeAttribute(RESUBMIT_DATA);
			}
		} catch (Exception e) {
		}
		super.afterCompletion(request, response, handler, ex);
	}

	/**
	 * 验证传参是否一至
	 *
	 * @param annotation
	 * @param request
	 * @return
	 */
	private boolean isResubmit(Resubmit annotation, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) return false;
		try {
			String params = "";
			switch (annotation.value()) {
				case json:
					params = getBodyString(request);
					break;
				case form_data:
					params = JSONUtils.obj2json(request.getParameterMap());
					break;
				case url:
					params = UUID.randomUUID().toString();
				case url_json:
					params += getBodyString(request);
					params += JSONUtils.obj2json(request.getParameterMap());
			}
			String url = request.getRequestURI();
			Map<String, String> map = new HashMap<String, String>();
			map.put(url, params);
			String nowUrlParams = map.toString();//
			Object preUrlParams = session.getAttribute(RESUBMIT_DATA);
			//如果上一个数据为null,表示还没有访问页面
			if (preUrlParams == null) {
				session.setAttribute(RESUBMIT_DATA, nowUrlParams);
				return false;
			}
			//否则，已经访问过页面
			else {
				//如果上次url+数据和本次url+数据相同，则表示城府添加数据
				if (preUrlParams.toString().equals(nowUrlParams)) {
					return true;
				}
				//如果上次 url+数据 和本次url加数据不同，则不是重复提交
				else {
					session.setAttribute(RESUBMIT_DATA, nowUrlParams);
					return false;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * 获取请求Body
	 *
	 * @param request
	 * @return
	 */
	private String getBodyString(HttpServletRequest request) {
		ShiroHttpServletRequest shiroHttpServletRequest = (ShiroHttpServletRequest) request;
		ServletRequest servletRequest = shiroHttpServletRequest.getRequest();
		String params = UUID.randomUUID().toString();
		if (servletRequest instanceof ResubmitRequestWrapper) {
			ResubmitRequestWrapper resubmitRequestWrapper = (ResubmitRequestWrapper) servletRequest;
			params = resubmitRequestWrapper.getBody();
		}
		return params;
	}


}
