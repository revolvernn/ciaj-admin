package com.ciaj.boot.modules.my.mapper;

import com.ciaj.boot.modules.my.entity.po.MyFamilyDebitPo;
import com.ciaj.base.Mapper;
import com.ciaj.boot.modules.my.entity.vo.MyFamilyDebitVo;
import com.ciaj.boot.modules.my.entity.dto.MyFamilyDebitDto;
import com.ciaj.boot.modules.sys.mapper.SysDictMapper;
import com.ciaj.boot.modules.sys.mapper.SysUserMapper;
import com.ciaj.comm.annotation.MultiTableJoins;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2026-08-04 19:44:41
 * @Description: www.ciaj.com gen DAO
 */
public interface MyFamilyDebitMapper extends Mapper<MyFamilyDebitPo, MyFamilyDebitDto, MyFamilyDebitVo> {
    /**
    * 多表列表查询
    * @param entity
    * @return
    */
    @MultiTableJoins(mappers = {SysUserMapper.class, SysDictMapper.class})
    List<MyFamilyDebitDto> selectDTOListMultiTable(MyFamilyDebitVo entity);
}
