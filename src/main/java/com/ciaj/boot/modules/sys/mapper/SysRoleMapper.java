package com.ciaj.boot.modules.sys.mapper;

import com.ciaj.base.Mapper;
import com.ciaj.boot.modules.sys.entity.dto.SysRoleDto;
import com.ciaj.boot.modules.sys.entity.po.SysRolePo;
import com.ciaj.boot.modules.sys.entity.vo.SysRoleVo;
import com.ciaj.comm.annotation.MultiTableJoins;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-09 15:46:23
 * @Description: www.ciaj.com DAO
 */
public interface SysRoleMapper extends Mapper<SysRolePo, SysRoleDto, SysRoleVo> {

	/**
	 * 根据用户ID查询角色
	 * @param userId
	 * @return
	 */
	@MultiTableJoins(mappers = {SysUserRoleRelMapper.class})
	List<SysRolePo> selectRolesByUserIdMultiTable(@Param("userId") String userId);

	@MultiTableJoins(mappers = {SysUserRoleRelMapper.class})
	List<SysRoleDto> selectDTOListMultiTable(SysRoleVo entity);
}
