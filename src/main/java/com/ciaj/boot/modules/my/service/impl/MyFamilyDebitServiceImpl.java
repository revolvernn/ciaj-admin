package com.ciaj.boot.modules.my.service.impl;

import com.ciaj.base.AbstractService;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.PageUtils;
import com.ciaj.comm.utils.Safes;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciaj.boot.modules.my.mapper.MyFamilyDebitMapper;
import com.ciaj.boot.modules.my.entity.po.MyFamilyDebitPo;
import com.ciaj.boot.modules.my.service.MyFamilyDebitService;
import com.ciaj.boot.modules.my.entity.vo.MyFamilyDebitVo;
import com.ciaj.boot.modules.my.entity.dto.MyFamilyDebitDto;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2026-08-04 19:44:41
 * @Description: www.ciaj.com gen service  实现
 */
@Service
public class MyFamilyDebitServiceImpl extends AbstractService<MyFamilyDebitPo, MyFamilyDebitDto, MyFamilyDebitVo> implements MyFamilyDebitService {

    @Autowired
    private MyFamilyDebitMapper myFamilyDebitMapper;

    @Override
    public Page<MyFamilyDebitDto> selectDTOListMultiTablePage(MyFamilyDebitVo entity) {
        com.github.pagehelper.Page page = PageUtils.startPageAndOrderBy();
        List<MyFamilyDebitDto> myFamilyDebitDtos = myFamilyDebitMapper.selectDTOListMultiTable(entity);
        return wrapPageDTO(page, myFamilyDebitDtos);
    }

    @Override
    public MyFamilyDebitDto selectById(String id) {

        MyFamilyDebitVo queryVo = new  MyFamilyDebitVo();
        queryVo.setId(id);
        List<MyFamilyDebitDto> myFamilyDebitDtos = myFamilyDebitMapper.selectDTOListMultiTable(queryVo);

        return Safes.of(myFamilyDebitDtos).findFirst().orElse(null);
    }
}
