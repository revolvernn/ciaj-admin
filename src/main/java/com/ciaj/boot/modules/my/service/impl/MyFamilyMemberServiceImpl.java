package com.ciaj.boot.modules.my.service.impl;

import com.ciaj.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciaj.boot.modules.my.mapper.MyFamilyMemberMapper;
import com.ciaj.boot.modules.my.entity.po.MyFamilyMemberPo;
import com.ciaj.boot.modules.my.service.MyFamilyMemberService;
import com.ciaj.boot.modules.my.entity.vo.MyFamilyMemberVo;
import com.ciaj.boot.modules.my.entity.dto.MyFamilyMemberDto;

/**
 * @Author: Ciaj.
 * @Date: 2026-01-14 16:48:58
 * @Description: www.ciaj.com gen service  实现
 */
@Service
public class MyFamilyMemberServiceImpl extends AbstractService<MyFamilyMemberPo, MyFamilyMemberDto, MyFamilyMemberVo> implements MyFamilyMemberService {

    @Autowired
    private MyFamilyMemberMapper myFamilyMemberMapper;
}
