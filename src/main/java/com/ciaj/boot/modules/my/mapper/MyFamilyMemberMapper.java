package com.ciaj.boot.modules.my.mapper;

import com.ciaj.boot.modules.my.entity.dto.MyFamilyBillDto;
import com.ciaj.boot.modules.my.entity.po.MyFamilyMemberPo;
import com.ciaj.base.Mapper;
import com.ciaj.boot.modules.my.entity.vo.MyFamilyBillVo;
import com.ciaj.boot.modules.my.entity.vo.MyFamilyMemberVo;
import com.ciaj.boot.modules.my.entity.dto.MyFamilyMemberDto;
import com.ciaj.boot.modules.sys.mapper.SysUserMapper;
import com.ciaj.comm.annotation.MultiTableJoins;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2026-01-14 16:48:58
 * @Description: www.ciaj.com gen DAO
 */
public interface MyFamilyMemberMapper extends Mapper<MyFamilyMemberPo, MyFamilyMemberDto, MyFamilyMemberVo> {
    /**
     * 列表查询
     * @param entity
     * @return
     */
    @MultiTableJoins(mappers = {SysUserMapper.class,MyFamilyMapper.class})
    List<MyFamilyMemberDto> selectDTOListMultiTable(MyFamilyMemberVo entity);
}
