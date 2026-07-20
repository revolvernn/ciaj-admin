package com.ciaj.boot.modules.my.service.impl;

import com.ciaj.base.AbstractService;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.PageUtils;
import com.ciaj.comm.utils.Safes;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciaj.boot.modules.my.mapper.MyFamilyMemberMapper;
import com.ciaj.boot.modules.my.entity.po.MyFamilyMemberPo;
import com.ciaj.boot.modules.my.service.MyFamilyMemberService;
import com.ciaj.boot.modules.my.entity.vo.MyFamilyMemberVo;
import com.ciaj.boot.modules.my.entity.dto.MyFamilyMemberDto;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2026-01-14 16:48:58
 * @Description: www.ciaj.com gen service  实现
 */
@Service
public class MyFamilyMemberServiceImpl extends AbstractService<MyFamilyMemberPo, MyFamilyMemberDto, MyFamilyMemberVo> implements MyFamilyMemberService {

    @Autowired
    private MyFamilyMemberMapper myFamilyMemberMapper;


    @Override
    public MyFamilyMemberDto selectById(String id) {

        MyFamilyMemberVo e = new MyFamilyMemberVo();
        e.setId(id);
        List<MyFamilyMemberDto> myFamilyMemberDtos = myFamilyMemberMapper.selectDTOListMultiTable(e);
        return Safes.of(myFamilyMemberDtos).findFirst().orElse(null);
    }

    @Override
    public Page<MyFamilyMemberDto> selectDTOListMultiTablePage(MyFamilyMemberVo entity) {
        com.github.pagehelper.Page page = PageUtils.startPageAndOrderBy();

        List<MyFamilyMemberDto> myFamilyMemberDtos = myFamilyMemberMapper.selectDTOListMultiTable(entity);

        return wrapPageDTO(page,myFamilyMemberDtos);
    }
}
