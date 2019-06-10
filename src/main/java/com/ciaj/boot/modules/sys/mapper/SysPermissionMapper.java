package com.ciaj.boot.modules.sys.mapper;

import com.ciaj.base.Mapper;
import com.ciaj.boot.modules.sys.entity.dto.SysPermissionDto;
import com.ciaj.boot.modules.sys.entity.po.SysPermissionPo;
import com.ciaj.boot.modules.sys.entity.vo.SysPermissionVo;
import com.ciaj.comm.annotation.MultiTableJoins;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-09 15:51:07
 * @Description: www.ciaj.com DAO
 */
public interface SysPermissionMapper extends Mapper<SysPermissionPo, SysPermissionDto, SysPermissionVo> {


	/**
	 * 根据用户ID查询权限
	 * @param userId
	 * @return
	 */
	@MultiTableJoins(mappers = {SysUserRoleRelMapper.class,SysRolePermissionRelMapper.class})
	List<SysPermissionPo> selectPermissionsByUserIdMultiTable(@Param("userId") String userId);
	/**
	 * 根据角色ID查询权限
	 * @param roleId
	 * @return
	 */
	@MultiTableJoins(mappers = {SysUserRoleRelMapper.class,SysRolePermissionRelMapper.class})
	List<SysPermissionPo> selectPermissionsByRoleIdMultiTable(@Param("roleId") String roleId);

	/**
	 * 获取当前用户下的权限列表
	 * @param entity
	 * @return
	 */
	@MultiTableJoins(mappers = {SysUserRoleRelMapper.class,SysRolePermissionRelMapper.class})
	List<SysPermissionDto> selectDTOListMultiTable(SysPermissionVo entity);
}
