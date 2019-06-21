package com.ciaj.comm.utils;

import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author Ciaj.
 * @Date 2019/4/10 15:00
 * @Version 1.0
 */
@SuppressWarnings("all")
public class StringUtil extends StringUtils {
	private static final Pattern pattern = Pattern.compile("\\{(.*?)\\}");
	private static Matcher matcher;
	public static final char UNDERLINE = '_';

	/**
	 * 格式化字符串 字符串中使用{key}表示占位符
	 *
	 * @param sourStr 需要匹配的字符串
	 * @param param   参数集
	 * @return
	 */
	public static String stringFormat(String sourStr, Map<String, Object> param) {
		String tagerStr = sourStr;
		if (param == null)
			return tagerStr;
		matcher = pattern.matcher(tagerStr);
		while (matcher.find()) {
			String key = matcher.group();
			String keyclone = key.substring(1, key.length() - 1).trim();
			Object value = param.get(keyclone);
			if (value != null)
				tagerStr = tagerStr.replace(key, value.toString());
		}
		return tagerStr;
	}

	/**
	 * 格式化字符串 字符串中使用{key}表示占位符 利用反射 自动获取对象属性值 (必须有get方法)
	 *
	 * @param sourStr 需要匹配的字符串
	 * @param obj     参数集
	 * @return
	 */
	public static String stringFormat(String sourStr, Object obj) {
		String tagerStr = sourStr;
		matcher = pattern.matcher(tagerStr);
		if (obj == null)
			return tagerStr;

		PropertyDescriptor pd;
		Method getMethod;
		// 匹配{}中间的内容 包括括号
		while (matcher.find()) {
			String key = matcher.group();
			String keyclone = key.substring(1, key.length() - 1).trim();
			try {
				pd = new PropertyDescriptor(keyclone, obj.getClass());
				getMethod = pd.getReadMethod();// 获得get方法
				Object value = getMethod.invoke(obj);
				if (value != null)
					tagerStr = tagerStr.replace(key, value.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tagerStr;
	}

	/**
	 * 格式化字符串 (替换所有) 字符串中使用{key}表示占位符
	 *
	 * @param sourStr 需要匹配的字符串
	 * @param param   参数集
	 * @return
	 */
	public static String stringFormatAll(String sourStr, Map<String, Object> param) {
		String tagerStr = sourStr;
		if (param == null)
			return tagerStr;
		matcher = pattern.matcher(tagerStr);
		while (matcher.find()) {
			String key = matcher.group();
			String keyclone = key.substring(1, key.length() - 1).trim();
			Object value = param.get(keyclone);
			if (value != null)
				tagerStr = tagerStr.replace(key, value.toString());
		}
		return tagerStr;
	}

	/**
	 * 拼接字符串
	 *
	 * @param separator 拼接参数：-、/、\、*
	 * @param strs      要拼接的参数
	 * @return String
	 */
	public static String getJoinString(String separator, String... strs) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strs.length; i++) {
			if (StringUtils.isNotBlank(strs[i])) {
				sb.append(strs[i]).append(separator);
			}
		}
		String join = sb.toString();
		if (StringUtils.isNotBlank(join)) {
			return join.substring(0, join.length() - 1);
		}
		return "";
	}

	/**
	 * 拼接字符串
	 *
	 * @param separator 拼接参数：-、/、\、*
	 * @param list      要拼接的参数
	 * @return String
	 */
	public static String getJoinString(String separator, List list) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (StringUtils.isNotBlank(list.get(i).toString())) {
				sb.append(list.get(i).toString()).append(separator);
			}
		}
		String join = sb.toString();
		if (StringUtils.isNotBlank(join)) {
			return join.substring(0, join.length() - 1);
		}
		return "";
	}

	/**
	 * 驼峰格式字符串转换为下划线格式字符串
	 *
	 * @param param
	 * @return
	 */
	public static String humpToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 下划线格式字符串转换为驼峰格式字符串
	 *
	 * @param param
	 * @return
	 */
	public static String underlineToHump(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 下划线格式字符串转换为驼峰格式字符串
	 *
	 * @param param
	 * @return
	 */
	public static String underlineToHumpUpperCase(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (i == 0) {
				c = Character.toUpperCase(c);
			}
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
