package com.ciaj.comm.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class RequestUtils {

	/**
	 * 获得用户远程地址,ip地址
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = null;
		try {
			ip = request.getHeader("x-forwarded-for");
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} catch (Exception e) {
			log.error("IPUtils ERROR ", e);
		}

		//使用代理，则获取第一个IP地址
		if (StringUtils.isEmpty(ip) && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}

		return ip;
	}


	/**
	 * 获得用户远程地址,ip地址
	 */
	public static String getRemoteAddr() {
		HttpServletRequest request = getRequest();
		String ip = null;
		try {
			ip = request.getHeader("x-forwarded-for");
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} catch (Exception e) {
			log.error("IPUtils ERROR ", e);
		}

		//使用代理，则获取第一个IP地址
		if (StringUtils.isEmpty(ip) && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}

		return ip;
	}

	/**
	 * 获取取cookie
	 *
	 * @param request
	 * @return
	 */
	public static Cookie[] getCookies(HttpServletRequest request) {
		return request.getCookies();
	}

	/**
	 * 根据cookie名获取
	 *
	 * @param name
	 * @param request
	 * @return
	 */
	public static Cookie getCookieByName(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return cookie;
				}
			}
		}
		return null;
	}

	/**
	 * 是否是Ajax异步请求
	 *
	 * @param request
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {

		String accept = request.getHeader("accept");
		String xRequestedWith = request.getHeader("X-Requested-With");

		// 如果是异步请求或是手机端，则直接返回信息
		return ((accept != null && accept.indexOf("application/json") != -1
				|| (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1)
		));
	}

	/**
	 * spring环境中获取当前请求对象
	 *
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		try {
			return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 获得站点url
	 *
	 * @return
	 */
	public static String getWebUrl() {
		final HttpServletRequest request = getRequest();
		String url = request.getScheme() + "://" + request.getServerName();
		if (request.getServerPort() != 80) {
			url += ":" + request.getServerPort();
		}
		url += request.getContextPath();
		return url;
	}

	/**
	 * 获取headers
	 *
	 * @param request
	 * @return
	 */
	public static String getHeaderText(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration enumeration = request.getHeaderNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		try {
			return JSONUtils.obj2json(map);
		} catch (Exception e) {
			log.error("getHeaderText", e);
		}
		return null;

	}

	public static boolean isStaticFile(HttpServletRequest request) {
		//线程中调用获取不到，作不是静态文件处理
		if (request == null) {
			return false;
		}
		String uri = request.getRequestURI();
		String staticFiles = ".css,.js,.png,.jpg,.gif,.jpeg,.bmp,.ico,.swf,.psd,.htc,.crx,.xpi,.exe,.ipa,.apk";

		if (StringUtils.endsWithAny(uri, staticFiles.split(","))) {
			return true;
		}
		return false;
	}

	/**
	 * 获取当前访问的域名
	 *
	 * @param request
	 * @param includeScheme 是否包含scheme
	 * @param includePort   是否包含接口
	 * @return http://localhost:8080
	 */
	public static String getDomain(HttpServletRequest request, boolean includeScheme, boolean includePort) {
		String _scheme = request.getScheme();
		String _serverName = request.getServerName();
		int _port = request.getServerPort();
		String r = _serverName;
		if (includeScheme) {
			r = _scheme + "://" + r;
		}
		if (includePort) {
			r = r + ":" + _port;
		}
		return r;
	}

	/**
	 * @param request
	 * @param includeContextPath
	 * @return
	 */
	public static String[] resolveRequestURI(HttpServletRequest request, boolean includeContextPath) {
		String[] r = null;
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();

		String siteRequestUri = requestURI;
		if (!includeContextPath && !StringUtils.isEmpty(contextPath)) {
			siteRequestUri = siteRequestUri.substring(contextPath.length());
		}
		r = siteRequestUri.split("/");
		return r;
	}

	/**
	 * 获取当前项目的实际地址存放路径
	 *
	 * @param request
	 * @return
	 */
	public static String getWebappRealPath(HttpServletRequest request) {
		return RequestUtils.getRequest().getSession().getServletContext().getRealPath("");
	}


}
