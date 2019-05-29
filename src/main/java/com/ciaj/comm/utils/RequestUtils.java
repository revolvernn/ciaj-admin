package com.ciaj.comm.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.Charset;
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

	/**
	 * 获取请求Body
	 *
	 * @param request
	 * @return
	 */
	public static String getBodyString(final ServletRequest request) {
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = null;
		BufferedReader reader = null;
		try {
			inputStream = cloneInputStream(request.getInputStream());
			reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return sb.toString();
	}


	/**
	 * Description: 复制输入流</br>
	 *
	 * @param inputStream
	 * @return</br>
	 */
	public static InputStream cloneInputStream(ServletInputStream inputStream) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		try {
			while ((len = inputStream.read(buffer)) > -1) {
				byteArrayOutputStream.write(buffer, 0, len);
			}
			byteArrayOutputStream.flush();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		return byteArrayInputStream;
	}


	/**
	 * 将路径中的路径分隔符校正为斜杠分隔符
	 *
	 * @param path
	 * @return
	 */
	public static String convertToSlash(String path) {
		String r = path;
		while (r.contains(FileUtils.slash_double) || r.contains(FileUtils.backslash_double)) {
			r = r.replace(FileUtils.slash_double, FileUtils.slash).replace(FileUtils.backslash_double, FileUtils.backslash);
		}
		r = r.replace(FileUtils.backslash, FileUtils.slash);

		return r;
	}

	/**
	 * 将path以斜杠分隔符开始
	 *
	 * @param path
	 * @return
	 */
	public static String wrapStartSlash(String path) {
		String r = path;
		while (r.startsWith(FileUtils.slash_double) || r.startsWith(FileUtils.backslash_double)) {
			r = FileUtils.slash + r.substring(2);
		}
		if (r.startsWith(FileUtils.slash) || r.startsWith(FileUtils.backslash)) {
			r = FileUtils.slash + r.substring(1);
		} else {
			r = FileUtils.slash + r;
		}
		return r;
	}

	/**
	 * 将path以斜杠分隔符结尾
	 *
	 * @param path
	 * @return
	 */
	public static String wrapEndSlash(String path) {
		String r = path;
		while (r.endsWith(FileUtils.slash_double) || r.endsWith(FileUtils.backslash_double)) {
			r = r.substring(0, r.length() - 2) + FileUtils.slash;
		}
		if (r.endsWith(FileUtils.slash) || r.endsWith(FileUtils.backslash)) {
			r = r.substring(0, r.length() - 1) + FileUtils.slash;
		} else {
			r = r + FileUtils.slash;
		}
		return r;
	}

	public static String unwrapStartSlash(String path) {
		String r = wrapStartSlash(path);
		return r.substring(1);
	}

	public static String unwrapEndSlash(String path) {
		String r = wrapEndSlash(path);
		return r.substring(0, r.length() - 1);
	}
}
