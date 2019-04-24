package com.ciaj.boot.modules.sys.mapper;

import com.ciaj.base.Mapper;
import com.ciaj.boot.modules.sys.entity.dto.SysMenuDto;
import com.ciaj.boot.modules.sys.entity.po.SysMenuPo;
import com.ciaj.boot.modules.sys.entity.vo.SysMenuVo;
import com.ciaj.comm.annotation.MultiTableJoins;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-09 15:53:23
 * @Description: www.ciaj.com DAO
 */
public interface SysMenuMapper extends Mapper<SysMenuPo, SysMenuDto, SysMenuVo> {
	/**
	 * 获取用户权限的导航菜单
	 *
	 * @param userId
	 *
	 * @return
	 */
	@MultiTableJoins(mappers = {SysUserRoleRelMapper.class,SysRolePermissionRelMapper.class,SysPermissionMapper.class})
	List<SysMenuPo> selectNavMultiTable(@Param("userId") String userId, @Param("roleId") String roleId);

	/**
	 * 获取导航菜单
	 * @param ids
	 * @return
	 */
	List<SysMenuPo> selectNavByIds(@Param("ids") Set<String> ids);
}
