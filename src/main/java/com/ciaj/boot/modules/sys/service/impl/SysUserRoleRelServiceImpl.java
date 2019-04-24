package com.ciaj.boot.modules.sys.service.impl;

import com.ciaj.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciaj.boot.modules.sys.mapper.SysUserRoleRelMapper;
import com.ciaj.boot.modules.sys.entity.po.SysUserRoleRelPo;
import com.ciaj.boot.modules.sys.service.SysUserRoleRelService;
import com.ciaj.boot.modules.sys.entity.vo.SysUserRoleRelVo;
import com.ciaj.boot.modules.sys.entity.dto.SysUserRoleRelDto;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 16:59:07
 * @Description: www.ciaj.com service  实现
 */
@Service
public class SysUserRoleRelServiceImpl extends AbstractService<SysUserRoleRelPo, SysUserRoleRelDto, SysUserRoleRelVo> implements SysUserRoleRelService {

    @Autowired
    private SysUserRoleRelMapper sysUserRoleRelMapper;
}
