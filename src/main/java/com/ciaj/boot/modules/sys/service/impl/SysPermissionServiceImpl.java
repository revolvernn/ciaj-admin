package com.ciaj.boot.modules.sys.service.impl;

import com.ciaj.base.AbstractService;
import com.ciaj.boot.modules.sys.entity.dto.SysPermissionDto;
import com.ciaj.boot.modules.sys.entity.po.SysPermissionPo;
import com.ciaj.boot.modules.sys.entity.vo.SysPermissionVo;
import com.ciaj.boot.modules.sys.mapper.SysPermissionMapper;
import com.ciaj.boot.modules.sys.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2018-12-27 14:43:55
 * @Description: www.ciaj.com service  实现
 */
@Service
public class SysPermissionServiceImpl extends AbstractService<SysPermissionPo, SysPermissionDto, SysPermissionVo> implements SysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public List<SysPermissionPo> selectPermissionsByUserId(String userId) {
        return sysPermissionMapper.selectPermissionsByUserIdMultiTable(userId);
    }

    @Override
    public List<SysPermissionPo> selectPermissionsByRoleId(String roleId) {
        return sysPermissionMapper.selectPermissionsByRoleIdMultiTable(roleId);
    }
}
