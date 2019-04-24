package com.ciaj.boot.modules.sys.service;

import com.ciaj.base.BaseService;
import com.ciaj.boot.modules.sys.entity.dto.SysRoleDto;
import com.ciaj.boot.modules.sys.entity.po.SysRolePo;
import com.ciaj.boot.modules.sys.entity.vo.SysRoleVo;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2018-12-27 15:17:28
 * @Description: www.ciaj.com service  接口
 */
public interface SysRoleService extends BaseService<SysRolePo, SysRoleDto, SysRoleVo> {

    /**
     * 根据用户ID查询角色
     *
     * @param userId
     *
     * @return
     */
    List<SysRolePo> selectRolesByUserId(String userId);
}
