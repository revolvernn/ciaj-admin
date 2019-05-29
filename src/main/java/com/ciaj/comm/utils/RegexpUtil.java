package com.ciaj.comm.utils;

/**
 * @Description TODO
 * @Author Ciaj.
 * @Date 2019/5/29 11:18
 * @Version 1.0
 */
public class RegexpUtil {
	/**
	 * 正整数或正浮点数
	 */
	public final static String REG_INTEGER_OR_FLOAT = "(^[1-9]\\d*\\.\\d*|^0\\.\\d*[1-9]\\d*$)|(^[1-9]\\d*$)";
	/**
	 * 绝对路径
	 */
	public final static String REG_ABSOLUTE_PATH = "(^/(\\w/?)+(\\w)$)|(^[a-zA-Z]{1}:(\\/(\\w)+)+(\\w)$)";
	/**
	 * RUL映射
	 */
	public final static String REG_URL_MAPPING = "^/(\\w/?)+(\\w)$";
	/**
	 * 匹配字母或数字或下划线或汉字 等价于 '[^A-Za-z0-9_]'
	 */
	public final static String REG_W = "^\\w+$";

	/**
	 * 手机号
	 */
	public static final String REG_MOBILE = "^((\\+?86)|(\\(\\+86\\)))?(1[23456789]\\d{9})$";
	/**
	 * 座机
	 */
	public static final String REG_PHONE = "^([0-9]{3,4}-)[0-9]{7,8}$";


}
