package com.ciaj.boot.modules.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ciaj.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciaj.boot.modules.sys.mapper.SysDictMapper;
import com.ciaj.boot.modules.sys.entity.po.SysDictPo;
import com.ciaj.boot.modules.sys.service.SysDictService;
import com.ciaj.boot.modules.sys.entity.vo.SysDictVo;
import com.ciaj.boot.modules.sys.entity.dto.SysDictDto;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 16:17:42
 * @Description: www.ciaj.com service  实现
 */
@Service
@DS("mydb")
public class SysDictServiceImpl extends AbstractService<SysDictPo, SysDictDto, SysDictVo> implements SysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;
}
