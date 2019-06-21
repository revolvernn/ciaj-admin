package com.ciaj.boot.modules.sys.service.impl;

import com.ciaj.base.AbstractService;
import com.ciaj.boot.modules.sys.entity.dto.SysDeptDto;
import com.ciaj.boot.modules.sys.entity.po.SysDeptPo;
import com.ciaj.boot.modules.sys.entity.vo.SysDeptVo;
import com.ciaj.boot.modules.sys.mapper.SysDeptMapper;
import com.ciaj.boot.modules.sys.service.SysDeptService;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2019-01-14 14:48:23
 * @Description: www.ciaj.com service  实现
 */
@Service
public class SysDeptServiceImpl extends AbstractService<SysDeptPo, SysDeptDto, SysDeptVo> implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;


    @Override
    public Page<SysDeptDto> selectDTOPage(SysDeptVo entity) {
        com.github.pagehelper.Page p = PageUtils.startPageAndOrderBy();
        List<SysDeptDto> sysDeptDtos = sysDeptMapper.selectDTOListMultiTable(entity);
        return wrapPageDTO(p, sysDeptDtos);
    }

    @Override
    public SysDeptDto selectById(String id) {
        SysDeptVo entity = new SysDeptVo();
        entity.setId(id);
        List<SysDeptDto> sysDeptDtos = sysDeptMapper.selectDTOListMultiTable(entity);
        return sysDeptDtos.get(0);
    }
}
