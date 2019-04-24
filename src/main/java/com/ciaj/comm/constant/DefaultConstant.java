package com.ciaj.comm.constant;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/4 10:15
 * @Description: 默认常量
 */
public class DefaultConstant {
	public final static String SUPER_ADMIN_ID = "1";
	public final static String LOGIN_USER = "loginUser";
	public final static String FLAG_Y = "Y";
	public final static String FLAG_N = "N";

	public enum UserRole {
		SUPER_ADMIN("superAdmin");

		public String value;

		UserRole(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}
}
