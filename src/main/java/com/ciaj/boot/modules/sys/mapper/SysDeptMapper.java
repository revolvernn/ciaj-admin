package com.ciaj.boot.modules.sys.mapper;

import com.ciaj.boot.modules.sys.entity.po.SysDeptPo;
import com.ciaj.base.Mapper;
import com.ciaj.boot.modules.sys.entity.vo.SysDeptVo;
import com.ciaj.boot.modules.sys.entity.dto.SysDeptDto;
import com.ciaj.comm.annotation.MultiTableJoins;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 16:09:47
 * @Description: www.ciaj.com DAO
 */
public interface SysDeptMapper extends Mapper<SysDeptPo, SysDeptDto, SysDeptVo> {

	@MultiTableJoins(mappers = {SysAreaMapper.class})
	List<SysDeptDto> selectDTOListMultiTable(SysDeptVo entity);
}
