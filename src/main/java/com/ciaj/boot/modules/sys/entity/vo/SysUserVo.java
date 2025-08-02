package com.ciaj.boot.modules.sys.entity.vo;

import com.ciaj.boot.modules.sys.entity.po.SysUserPo;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 17:02:08
 * @Description: www.ciaj.com vo
 */
public class SysUserVo extends SysUserPo {

    /**
     * 角色编码
     */
    private String roleCode;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
