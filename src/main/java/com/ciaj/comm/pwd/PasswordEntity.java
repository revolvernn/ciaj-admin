package com.ciaj.comm.pwd;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/19 12:37
 * @Description: 密码
 */
@Data
@AllArgsConstructor
public class PasswordEntity {
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 密码盐
	 */
	private String salt;

	public PasswordEntity(){}
}
