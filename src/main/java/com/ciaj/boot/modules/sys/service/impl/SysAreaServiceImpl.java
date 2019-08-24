package com.ciaj.boot.modules.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ciaj.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciaj.boot.modules.sys.mapper.SysAreaMapper;
import com.ciaj.boot.modules.sys.entity.po.SysAreaPo;
import com.ciaj.boot.modules.sys.service.SysAreaService;
import com.ciaj.boot.modules.sys.entity.vo.SysAreaVo;
import com.ciaj.boot.modules.sys.entity.dto.SysAreaDto;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 15:54:06
 * @Description: www.ciaj.com service  实现
 */
@Service
@DS("mydb")
public class SysAreaServiceImpl extends AbstractService<SysAreaPo, SysAreaDto, SysAreaVo> implements SysAreaService {

    @Autowired
    private SysAreaMapper sysAreaMapper;
}
