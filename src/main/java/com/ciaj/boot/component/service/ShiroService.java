package com.ciaj.boot.component.service;

import com.ciaj.boot.component.config.shiro.ShiroUser;

/**
 * @Author: Ciaj.
 * @Date: 2019/1/4 14:25
 * @Description:
 */
public interface ShiroService {

    /**
     * 查询权限用户
     *
     * @param userId
     *
     * @return
     */
    public ShiroUser selectShiroUser(String userId);
    /**
     * 查询权限用户
     *
     * @param account
     *
     * @return
     */
    public ShiroUser selectShiroUserByAccount(String account) ;

    /**
     * 更新当前用户角色
     *
     * @param roleId
     */
    public ShiroUser updateShiroUser(String roleId);
}
