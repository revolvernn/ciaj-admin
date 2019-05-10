package com.ciaj.comm.utils;

import com.ciaj.comm.exception.BsRException;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @Author: Ciaj.
 * @Date: 2018/12/4 13:24
 * @Description:
 */
@Log4j2
public class ShiroUtils {
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static boolean checkPermissions(String permission) {
		try {
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.checkPermission(permission);
			return true;
		} catch (AuthorizationException e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	public static void logout() {
		SecurityUtils.getSubject().logout();
	}

	public static String getKaptcha(String key) {
		Object kaptcha = getSessionAttribute(key);
		if (kaptcha == null) {
			throw new BsRException("验证码已失效");
		}
		getSession().removeAttribute(key);
		return kaptcha.toString();
	}
}
