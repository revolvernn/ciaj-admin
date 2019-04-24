package com.ciaj.boot.modules.sys.service.impl;

import com.ciaj.base.AbstractService;
import com.ciaj.boot.modules.sys.entity.dto.SysRoleDto;
import com.ciaj.boot.modules.sys.entity.po.SysRolePo;
import com.ciaj.boot.modules.sys.entity.vo.SysRoleVo;
import com.ciaj.boot.modules.sys.mapper.SysRoleMapper;
import com.ciaj.boot.modules.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2018-12-27 15:17:28
 * @Description: www.ciaj.com service  实现
 */
@Service
public class SysRoleServiceImpl extends AbstractService<SysRolePo, SysRoleDto, SysRoleVo> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRolePo> selectRolesByUserId(String userId) {
        return sysRoleMapper.selectRolesByUserIdMultiTable(userId);
    }
}
