package com.ciaj.boot.modules.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ciaj.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciaj.boot.modules.sys.mapper.SysAuthMapper;
import com.ciaj.boot.modules.sys.entity.po.SysAuthPo;
import com.ciaj.boot.modules.sys.service.SysAuthService;
import com.ciaj.boot.modules.sys.entity.vo.SysAuthVo;
import com.ciaj.boot.modules.sys.entity.dto.SysAuthDto;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 15:58:51
 * @Description: www.ciaj.com service  实现
 */
@Service
@DS("mydb")
public class SysAuthServiceImpl extends AbstractService<SysAuthPo, SysAuthDto, SysAuthVo> implements SysAuthService {

    @Autowired
    private SysAuthMapper sysAuthMapper;
}
