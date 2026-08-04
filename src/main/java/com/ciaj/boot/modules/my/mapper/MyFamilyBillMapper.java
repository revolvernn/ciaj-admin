package com.ciaj.boot.modules.my.mapper;

import com.ciaj.base.Mapper;
import com.ciaj.boot.modules.my.entity.dto.MyFamilyBillDto;
import com.ciaj.boot.modules.my.entity.po.MyFamilyBillPo;
import com.ciaj.boot.modules.my.entity.vo.MyFamilyBillVo;
import com.ciaj.boot.modules.sys.mapper.SysDictMapper;
import com.ciaj.boot.modules.sys.mapper.SysUserMapper;
import com.ciaj.comm.annotation.MultiTableJoins;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2026-07-13 22:34:01
 * @Description: www.ciaj.com gen DAO
 */
public interface MyFamilyBillMapper extends Mapper<MyFamilyBillPo, MyFamilyBillDto, MyFamilyBillVo> {

    /**
     * 列表查询
     * @param entity
     * @return
     */
    @MultiTableJoins(mappers = {SysUserMapper.class, SysDictMapper.class})
    List<MyFamilyBillDto> selectDTOListMultiTable(MyFamilyBillVo entity);
}
