package com.ciaj.boot.modules.sys.service.impl;

import com.ciaj.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciaj.boot.modules.sys.mapper.DemoTableMapper;
import com.ciaj.boot.modules.sys.entity.po.DemoTablePo;
import com.ciaj.boot.modules.sys.service.DemoTableService;
import com.ciaj.boot.modules.sys.entity.vo.DemoTableVo;
import com.ciaj.boot.modules.sys.entity.dto.DemoTableDto;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 17:54:57
 * @Description: www.ciaj.com service  实现
 */
@Service
public class DemoTableServiceImpl extends AbstractService<DemoTablePo, DemoTableDto, DemoTableVo> implements DemoTableService {

    @Autowired
    private DemoTableMapper demoTableMapper;
}
