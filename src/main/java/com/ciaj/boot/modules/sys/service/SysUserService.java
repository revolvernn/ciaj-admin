package com.ciaj.boot.modules.sys.service;

import com.ciaj.base.BaseService;
import com.ciaj.boot.modules.sys.entity.po.SysUserPo;
import com.ciaj.boot.modules.sys.entity.vo.SysUserVo;
import com.ciaj.boot.modules.sys.entity.dto.SysUserDto;
import com.ciaj.comm.utils.Page;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 17:02:08
 * @Description: www.ciaj.com service  接口
 */
public interface SysUserService extends BaseService<SysUserPo, SysUserDto, SysUserVo> {

	/**
	 * 根据ID获取用户
	 * @param userId
	 * @return
	 */
	public SysUserDto selectById(String userId);

	/**
	 * 获取用户列表
	 * @param entity VO
	 * @return
	 */
	@Override
	Page<SysUserDto> selectDTOPage(SysUserVo entity);
}
