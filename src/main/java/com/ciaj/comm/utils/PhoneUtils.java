package com.ciaj.comm.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author Ciaj.
 * @Date 2019/4/8 13:13
 * @Version 1.0
 */
@Slf4j
public class PhoneUtils {
	/**
	 * 手机号
	 */
	public static final String REGEX_MOBILE = "^((\\+?86)|(\\(\\+86\\)))?(1[23456789]\\d{9})$";
	/**
	 * 座机
	 */
	public static final String REGEX_PHONE = "^([0-9]{3,4}-)[0-9]{7,8}$";

	/**
	 * 手机号隐藏中间四位 111****1111
	 *
	 * @param phone
	 * @return
	 */
	public static String hidePhone(String phone) {
		if (StringUtils.isNotEmpty(phone) && Pattern.matches(REGEX_MOBILE, phone)) {
			return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
		}
		return phone;
	}

	/**
	 * 手机号字段隐藏中间四位
	 *
	 * @param fieldName 属性名
	 * @param record    对象
	 * @param <T>       对象
	 */
	public static <T> void hidePhone(String fieldName, T record) {
		if (record == null || StringUtils.isEmpty(fieldName)) return;
		if (record instanceof Map) {
			Map map = (Map) record;
			map.put(fieldName, hidePhone(map.get(fieldName).toString()));
		} else {
			try {
				List<Class<?>> superClass = getSuperClass(record.getClass());
				Field field = null;
				for (Class<?> aClass : superClass) {
					field = getClassField(fieldName, aClass);
					if (field != null) {
						break;
					}
				}
				if (field == null) {
					field = record.getClass().getDeclaredField(fieldName);
				}
				field.setAccessible(true);
				Object value = field.get(record);
				if (value != null) {
					field.set(record, hidePhone(value.toString()));
				}
				field.setAccessible(false);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
	}

	public static void main(String[] args) {
		Map<String, Object> map = new HashMap();
		map.put("phone", "18502316666");
		map.put("name", "abc");
		hidePhone("phone", map);

		System.out.println(map.get("phone").toString());
	}

	/**
	 * 手机号字段隐藏中间四位
	 *
	 * @param fieldName 属性名
	 * @param records   对象List
	 * @param <T>       对象
	 */
	public static <T> void hidePhones(String fieldName, List<T> records) {
		if (CollectionUtils.isEmpty(records) || StringUtils.isEmpty(fieldName)) return;
		for (T record : records) {
			hidePhone(fieldName, record);
		}
	}

	private static Field getField(String fieldName, Class record) {
		Field field = null;
		try {
			field = record.getSuperclass().getDeclaredField(fieldName);
			if (field == null) {
				field = record.getDeclaredField(fieldName);
			}
		} catch (NoSuchFieldException e) {
			log.error(e.getMessage(),e);
		}
		return field;
	}

	private static Field getClassField(String fieldName, Class record) {
		try {
		 	return record.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}

	/**
	 * 获取这个类的所有父类
	 *
	 * @param clazz
	 * @return
	 */
	public static List<Class<?>> getSuperClass(Class<?> clazz) {
		List<Class<?>> clazzs = new ArrayList<Class<?>>();
		clazzs.add(clazz);
		Class<?> suCl = clazz.getSuperclass();
		while (suCl != null) {
			clazzs.add(suCl);
			suCl = suCl.getSuperclass();
		}
		return clazzs;
	}

}
