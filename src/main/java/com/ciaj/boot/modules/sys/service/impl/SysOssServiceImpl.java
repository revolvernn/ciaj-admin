package com.ciaj.boot.modules.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ciaj.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciaj.boot.modules.sys.mapper.SysOssMapper;
import com.ciaj.boot.modules.sys.entity.po.SysOssPo;
import com.ciaj.boot.modules.sys.service.SysOssService;
import com.ciaj.boot.modules.sys.entity.vo.SysOssVo;
import com.ciaj.boot.modules.sys.entity.dto.SysOssDto;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 16:35:33
 * @Description: www.ciaj.com service  实现
 */
@Service
@DS("mydb")
public class SysOssServiceImpl extends AbstractService<SysOssPo, SysOssDto, SysOssVo> implements SysOssService {

    @Autowired
    private SysOssMapper sysOssMapper;
}
