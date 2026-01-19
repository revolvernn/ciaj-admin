package com.ciaj.boot.modules.my.service.impl;

import com.ciaj.base.AbstractService;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciaj.boot.modules.my.mapper.MyFamilyMapper;
import com.ciaj.boot.modules.my.entity.po.MyFamilyPo;
import com.ciaj.boot.modules.my.service.MyFamilyService;
import com.ciaj.boot.modules.my.entity.vo.MyFamilyVo;
import com.ciaj.boot.modules.my.entity.dto.MyFamilyDto;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2026-01-14 16:21:01
 * @Description: www.ciaj.com gen service  实现
 */
@Service
public class MyFamilyServiceImpl extends AbstractService<MyFamilyPo, MyFamilyDto, MyFamilyVo> implements MyFamilyService {

    @Autowired
    private MyFamilyMapper myFamilyMapper;


    @Override
    public Page<MyFamilyDto> selectDTOPage(MyFamilyVo entity) {
        com.github.pagehelper.Page p = PageUtils.startPageAndOrderBy();
        List<MyFamilyDto> sysDeptDtos = myFamilyMapper.selectDTOListMultiTable(entity);
        return wrapPageDTO(p, sysDeptDtos);
    }

    @Override
    public MyFamilyDto selectById(String id) {
        MyFamilyVo entity = new MyFamilyVo();
        entity.setId(id);
        List<MyFamilyDto> sysDeptDtos = myFamilyMapper.selectDTOListMultiTable(entity);
        return sysDeptDtos.get(0);
    }
}
