package com.ciaj.boot.modules.my.service.impl;

import com.ciaj.base.AbstractService;
import com.ciaj.boot.modules.sys.mapper.SysUserMapper;
import com.ciaj.boot.modules.sys.service.SysUserService;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.PageUtils;
import com.ciaj.comm.utils.Safes;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciaj.boot.modules.my.mapper.MyFamilyBillMapper;
import com.ciaj.boot.modules.my.entity.po.MyFamilyBillPo;
import com.ciaj.boot.modules.my.service.MyFamilyBillService;
import com.ciaj.boot.modules.my.entity.vo.MyFamilyBillVo;
import com.ciaj.boot.modules.my.entity.dto.MyFamilyBillDto;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2026-07-13 22:34:02
 * @Description: www.ciaj.com gen service  实现
 */
@Service
public class MyFamilyBillServiceImpl extends AbstractService<MyFamilyBillPo, MyFamilyBillDto, MyFamilyBillVo> implements MyFamilyBillService {

    @Autowired
    private MyFamilyBillMapper myFamilyBillMapper;


    @Override
    public Page<MyFamilyBillDto> selectDTOListMultiTablePage(MyFamilyBillVo entity) {
        com.github.pagehelper.Page page = PageUtils.startPageAndOrderBy();
        List<MyFamilyBillDto> myFamilyBillDtos = myFamilyBillMapper.selectDTOListMultiTable(entity);
        return wrapPageDTO(page, myFamilyBillDtos);
    }

    @Override
    public MyFamilyBillDto selectById(String id) {

        MyFamilyBillVo myFamilyBillVo = new MyFamilyBillVo();
        myFamilyBillVo.setId(id);
        List<MyFamilyBillDto> myFamilyBillDtos = myFamilyBillMapper.selectDTOListMultiTable(myFamilyBillVo);

        return Safes.of(myFamilyBillDtos).findFirst().orElse(null);
    }
}
