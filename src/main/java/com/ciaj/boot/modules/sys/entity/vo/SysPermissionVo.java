package com.ciaj.boot.modules.sys.entity.vo;

import com.ciaj.boot.modules.sys.entity.po.SysPermissionPo;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 16:41:56
 * @Description: www.ciaj.com vo
 */
public class SysPermissionVo extends SysPermissionPo {
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户角色ID
	 */
	private String roleId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
