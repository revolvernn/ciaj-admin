package com.ciaj.boot.modules.sys.mapper;

import com.ciaj.base.Mapper;
import com.ciaj.boot.modules.sys.entity.dto.SysUserDto;
import com.ciaj.boot.modules.sys.entity.po.SysUserPo;
import com.ciaj.boot.modules.sys.entity.vo.SysUserVo;
import com.ciaj.comm.annotation.MultiTableJoins;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 17:02:08
 * @Description: www.ciaj.com DAO
 */
public interface SysUserMapper extends Mapper<SysUserPo, SysUserDto, SysUserVo> {
	/**
	 * 获取用户列表
	 * @param entity
	 * @return
	 */
	@MultiTableJoins(mappers = {SysAreaMapper.class,SysDeptMapper.class})
	List<SysUserDto> selectDTOListMultiTable(SysUserVo entity);
}
