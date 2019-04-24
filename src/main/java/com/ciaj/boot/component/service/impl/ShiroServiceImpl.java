package com.ciaj.boot.component.service.impl;

import com.ciaj.boot.component.config.shiro.ShiroUser;
import com.ciaj.boot.component.service.ShiroService;
import com.ciaj.boot.modules.sys.entity.po.SysRolePo;
import com.ciaj.boot.modules.sys.entity.po.SysUserPo;
import com.ciaj.boot.modules.sys.service.SysPermissionService;
import com.ciaj.boot.modules.sys.service.SysRoleService;
import com.ciaj.boot.modules.sys.service.SysUserService;
import com.ciaj.comm.constant.DefaultConstant;
import com.ciaj.comm.utils.CommUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2019/1/4 14:25
 * @Description:
 */
@Service
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    public SysUserService sysUserService;
    @Autowired
    public SysRoleService sysRoleService;
    @Autowired
    public SysPermissionService sysPermissionService;


    @Override
    public ShiroUser selectShiroUser(String userId) {
        final SysUserPo sysUser = sysUserService.selectByPrimaryKey(userId);
        ShiroUser shiroUser = new ShiroUser();
        BeanUtils.copyProperties(sysUser, shiroUser);
        final List<SysRolePo> sysRoles = sysRoleService.selectRolesByUserId(userId);
        if (CollectionUtils.isNotEmpty(sysRoles)) {
            shiroUser.setRoles(sysRoles);
            shiroUser.setRole(sysRoles.get(0));
            shiroUser.setPermissions(sysPermissionService.selectPermissionsByRoleId(shiroUser.getRole().getId()));
        }
        return shiroUser;
    }

    @Override
    public ShiroUser updateShiroUser(String roleId) {
        ShiroUser loginUser = CommUtil.getLoginUser();
        final SysRolePo sysRole = sysRoleService.selectByPrimaryKey(roleId);
        loginUser.setRole(sysRole);
        loginUser.setPermissions(sysPermissionService.selectPermissionsByRoleId(roleId));

        SecurityUtils.getSubject().getSession().setAttribute(DefaultConstant.LOGIN_USER, loginUser);
        return loginUser;
    }

}
