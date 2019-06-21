package com.ciaj.boot.component.config.shiro;

import com.ciaj.boot.modules.sys.entity.po.SysPermissionPo;
import com.ciaj.boot.modules.sys.entity.po.SysRolePo;
import com.ciaj.boot.modules.sys.entity.po.SysUserPo;
import com.ciaj.comm.constant.DefaultConstant;
import com.ciaj.comm.utils.CollectionUtil;
import com.ciaj.comm.utils.StringUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/19 17:36
 * @Description:
 */
public class ShiroUser extends SysUserPo {
	private static final long serialVersionUID = 2028066224674954121L;
	/**
	 * 当前角色
	 */
	private SysRolePo role;
	/**
	 * 角色
	 */
	private List<SysRolePo> roles;
	/**
	 * 权限
	 */
	private List<SysPermissionPo> permissions;

	private boolean isSuperAdmin = Boolean.FALSE;

	public SysRolePo getRole() {
		return role;
	}

	public void setRole(SysRolePo role) {
		this.role = role;
	}

	public List<SysRolePo> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRolePo> roles) {
		this.roles = roles;
	}

	public List<SysPermissionPo> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<SysPermissionPo> permissions) {
		this.permissions = permissions;
	}

	public Set<String> getStringRoles() {
		Set<String> stringSet = new HashSet<>();
		if (role != null) {
			if (StringUtil.isNotBlank(role.getCode())) {
				String[] split = role.getCode().split(",");
				stringSet.addAll(Arrays.asList(split));
			}
		}
		return stringSet;
	}

	public Set<String> getStringPermissions() {
		Set<String> stringSet = new HashSet<>();
		if (CollectionUtil.isNotEmpty(permissions)) {
			for (SysPermissionPo permission : permissions) {
				if (StringUtil.isNotBlank(permission.getPermissionCode())) {
					String[] split = permission.getPermissionCode().split(",");
					stringSet.addAll(Arrays.asList(split));
				}
			}
		}
		return stringSet;
	}

	public boolean isSuperAdmin() {
		if ((role != null && role.getCode().equals(DefaultConstant.UserRole.SUPER_ADMIN.value)) || super.getId().equals(DefaultConstant.SUPER_ADMIN_ID)) {
			return !isSuperAdmin;
		}
		return isSuperAdmin;
	}
}
