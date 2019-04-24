package com.ciaj.boot.modules.sys.service.impl;

import com.ciaj.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciaj.boot.modules.sys.mapper.SysUserMapper;
import com.ciaj.boot.modules.sys.entity.po.SysUserPo;
import com.ciaj.boot.modules.sys.service.SysUserService;
import com.ciaj.boot.modules.sys.entity.vo.SysUserVo;
import com.ciaj.boot.modules.sys.entity.dto.SysUserDto;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 17:02:08
 * @Description: www.ciaj.com service  实现
 */
@Service
public class SysUserServiceImpl extends AbstractService<SysUserPo, SysUserDto, SysUserVo> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
}
