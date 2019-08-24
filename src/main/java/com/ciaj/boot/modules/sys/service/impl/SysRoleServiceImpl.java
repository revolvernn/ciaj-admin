package com.ciaj.boot.modules.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ciaj.base.AbstractService;
import com.ciaj.boot.component.config.shiro.ShiroUser;
import com.ciaj.boot.modules.sys.entity.dto.SysRoleDto;
import com.ciaj.boot.modules.sys.entity.po.SysRolePo;
import com.ciaj.boot.modules.sys.entity.vo.SysRoleVo;
import com.ciaj.boot.modules.sys.mapper.SysRoleMapper;
import com.ciaj.boot.modules.sys.service.SysRoleService;
import com.ciaj.comm.constant.DefaultConstant;
import com.ciaj.comm.utils.CommUtil;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2018-12-27 15:17:28
 * @Description: www.ciaj.com service  实现
 */
@Service
@DS("mydb")
public class SysRoleServiceImpl extends AbstractService<SysRolePo, SysRoleDto, SysRoleVo> implements SysRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Override
	public List<SysRolePo> selectRolesByUserId(String userId) {
		SysRoleVo vo = new SysRoleVo();
		vo.setAvailable(DefaultConstant.FLAG_Y);
		vo.setDelFlag(DefaultConstant.FLAG_N);
		return sysRoleMapper.selectRolesByUserIdMultiTable(userId);
	}

	@Override
	public Page<SysRoleDto> selectDTOPage(SysRoleVo entity) {
		ShiroUser loginUser = CommUtil.getLoginUser();
		if (loginUser.isSuperAdmin()) {
			return super.selectDTOPage(entity);
		}else {
			entity.setUserId(loginUser.getId());
		}
		com.github.pagehelper.Page p = PageUtils.startPageAndOrderBy();
		List<SysRoleDto> sysRolePos = sysRoleMapper.selectDTOListMultiTable(entity);
		return wrapPageDTO(p, sysRolePos);
	}
}
