package com.ciaj.boot.modules.sys.entity.vo;

import com.ciaj.boot.modules.sys.entity.po.SysRolePo;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 16:48:09
 * @Description: www.ciaj.com vo
 */
public class SysRoleVo extends SysRolePo {
	/**
	 * 用户ID
	 */
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
