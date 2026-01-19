package com.ciaj.boot.modules.my.mapper;

import com.ciaj.boot.modules.my.entity.po.MyFamilyPo;
import com.ciaj.base.Mapper;
import com.ciaj.boot.modules.my.entity.vo.MyFamilyVo;
import com.ciaj.boot.modules.my.entity.dto.MyFamilyDto;
import com.ciaj.boot.modules.sys.mapper.SysAreaMapper;
import com.ciaj.comm.annotation.MultiTableJoins;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2026-01-14 16:21:01
 * @Description: www.ciaj.com gen DAO
 */
public interface MyFamilyMapper extends Mapper<MyFamilyPo, MyFamilyDto, MyFamilyVo> {

    @MultiTableJoins(mappers = {SysAreaMapper.class})
    List<MyFamilyDto> selectDTOListMultiTable(MyFamilyVo entity);
}
