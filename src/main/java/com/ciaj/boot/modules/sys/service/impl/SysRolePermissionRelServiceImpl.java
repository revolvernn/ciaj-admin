package com.ciaj.boot.modules.sys.service.impl;

import com.ciaj.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciaj.boot.modules.sys.mapper.SysRolePermissionRelMapper;
import com.ciaj.boot.modules.sys.entity.po.SysRolePermissionRelPo;
import com.ciaj.boot.modules.sys.service.SysRolePermissionRelService;
import com.ciaj.boot.modules.sys.entity.vo.SysRolePermissionRelVo;
import com.ciaj.boot.modules.sys.entity.dto.SysRolePermissionRelDto;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 16:54:59
 * @Description: www.ciaj.com service  实现
 */
@Service
public class SysRolePermissionRelServiceImpl extends AbstractService<SysRolePermissionRelPo, SysRolePermissionRelDto, SysRolePermissionRelVo> implements SysRolePermissionRelService {

    @Autowired
    private SysRolePermissionRelMapper sysRolePermissionRelMapper;
}
