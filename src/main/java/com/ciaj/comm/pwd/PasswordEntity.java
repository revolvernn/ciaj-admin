package com.ciaj.comm.pwd;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/19 12:37
 * @Description: 密码
 */
public class PasswordEntity {
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 密码盐
	 */
	private String salt;

	public PasswordEntity() {
	}

	public PasswordEntity(String password, String salt) {
		this.password = password;
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}
