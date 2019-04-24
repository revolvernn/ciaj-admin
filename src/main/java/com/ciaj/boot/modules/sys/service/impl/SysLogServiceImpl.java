package com.ciaj.boot.modules.sys.service.impl;

import com.ciaj.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciaj.boot.modules.sys.mapper.SysLogMapper;
import com.ciaj.boot.modules.sys.entity.po.SysLogPo;
import com.ciaj.boot.modules.sys.service.SysLogService;
import com.ciaj.boot.modules.sys.entity.vo.SysLogVo;
import com.ciaj.boot.modules.sys.entity.dto.SysLogDto;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 16:26:57
 * @Description: www.ciaj.com service  实现
 */
@Service
public class SysLogServiceImpl extends AbstractService<SysLogPo, SysLogDto, SysLogVo> implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;
}
