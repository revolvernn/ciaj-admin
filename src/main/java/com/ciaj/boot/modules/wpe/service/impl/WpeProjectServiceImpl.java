package com.ciaj.boot.modules.wpe.service.impl;

import com.ciaj.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciaj.boot.modules.wpe.mapper.WpeProjectMapper;
import com.ciaj.boot.modules.wpe.entity.po.WpeProjectPo;
import com.ciaj.boot.modules.wpe.service.WpeProjectService;
import com.ciaj.boot.modules.wpe.entity.vo.WpeProjectVo;
import com.ciaj.boot.modules.wpe.entity.dto.WpeProjectDto;

/**
 * @Author: Ciaj.
 * @Date: 2025-07-27 09:31:59
 * @Description: www.ciaj.com gen service  实现
 */
@Service
public class WpeProjectServiceImpl extends AbstractService<WpeProjectPo, WpeProjectDto, WpeProjectVo> implements WpeProjectService {

    @Autowired
    private WpeProjectMapper wpeProjectMapper;
}
