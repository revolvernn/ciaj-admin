package com.ciaj.boot.modules.sys.service.impl;

import com.ciaj.base.AbstractService;
import com.ciaj.boot.component.config.shiro.ShiroUser;
import com.ciaj.boot.modules.sys.entity.dto.SysPermissionDto;
import com.ciaj.boot.modules.sys.entity.po.SysMenuPo;
import com.ciaj.boot.modules.sys.entity.po.SysPermissionPo;
import com.ciaj.boot.modules.sys.entity.po.SysRolePo;
import com.ciaj.boot.modules.sys.entity.vo.SysPermissionVo;
import com.ciaj.boot.modules.sys.mapper.SysPermissionMapper;
import com.ciaj.boot.modules.sys.service.SysMenuService;
import com.ciaj.boot.modules.sys.service.SysPermissionService;
import com.ciaj.comm.constant.DefaultConstant;
import com.ciaj.comm.utils.CommUtil;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.PageUtis;
import com.ciaj.comm.utils.StringUtli;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2018-12-27 14:43:55
 * @Description: www.ciaj.com service  实现
 */
@Service
public class SysPermissionServiceImpl extends AbstractService<SysPermissionPo, SysPermissionDto, SysPermissionVo> implements SysPermissionService {

	@Autowired
	private SysPermissionMapper sysPermissionMapper;

	@Autowired
	private SysMenuService sysMenuService;

	@Override
	public List<SysPermissionPo> selectPermissionsByUserId(String userId) {
		return sysPermissionMapper.selectPermissionsByUserIdMultiTable(userId);
	}

	@Override
	public List<SysPermissionPo> selectPermissionsByRoleId(String roleId) {
		return sysPermissionMapper.selectPermissionsByRoleIdMultiTable(roleId);
	}

	@Override
	public Page<SysPermissionDto> selectDTOPage(SysPermissionVo entity) {
		ShiroUser loginUser = CommUtil.getLoginUser();
		if (loginUser.isSuperAdmin()) {
			return super.selectDTOPage(entity);
		} else {
			entity.setUserId(loginUser.getId());
			SysRolePo role = loginUser.getRole();
			if (role != null) {
				entity.setRoleId(role.getId());
			}
		}
		com.github.pagehelper.Page p = PageUtis.startPageAndOrderBy();
		List<SysPermissionDto> list = sysPermissionMapper.selectDTOListMultiTable(entity);
		return wrapPageDTO(p, list);
	}

	@Override
	public SysPermissionDto getById(String id) {
		SysPermissionPo sysPermissionPo = super.selectByPrimaryKey(id);
		SysPermissionDto sysPermissionDto = poToDto(sysPermissionPo);
		if(DefaultConstant.PermissionType.menu.name().equals(sysPermissionDto.getType()) && StringUtli.isNotBlank(sysPermissionDto.getUrl())){
			SysMenuPo sysMenuPo = sysMenuService.selectByPrimaryKey(sysPermissionDto.getUrl());
			sysPermissionDto.setMenu(sysMenuService.poToDto(sysMenuPo));
		}
		return sysPermissionDto;
	}
}
