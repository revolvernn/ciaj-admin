package com.ciaj.boot.modules.sys.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.sys.entity.dto.SysUserRoleRelDto;
import com.ciaj.boot.modules.sys.entity.po.SysUserRoleRelPo;
import com.ciaj.boot.modules.sys.entity.vo.SysUserRoleRelVo;
import com.ciaj.boot.modules.sys.service.SysUserRoleRelService;
import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.annotation.OperationLog;
import com.ciaj.comm.annotation.Resubmit;
import com.ciaj.comm.utils.CollectionUtil;
import com.ciaj.comm.utils.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 16:59:07
 * @Description: www.ciaj.com mvc api
 */
@Api(tags = "系统角色权限关联-管理")
@ResponseBody
@Controller
@RequestMapping("sys/user/role/rel")
public class SysUserRoleRelController extends AbstractController<SysUserRoleRelPo, SysUserRoleRelDto, SysUserRoleRelVo> {

	@Autowired
	private SysUserRoleRelService sysUserRoleRelService;

	/**
	 * 列表
	 *
	 * @return
	 */
	@ApiOperation("获取系统角色权限关联列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
			@ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
			@ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
			@ApiImplicitParam(name = "roleId", value = "角色ID", paramType = "query"),
			@ApiImplicitParam(name = "userId", value = "用户ID", paramType = "query")
	})
	@OperationLog(operation = "系统角色权限关联-管理", content = "获取系统角色权限关联列表")
	@RequiresPermissions("sys:user:role:rel:list")
	@GetMapping("list")
	public ResponseEntity<Page<SysUserRoleRelDto>> list(String keyword, String roleId, String userId) {
		SysUserRoleRelVo entity = new SysUserRoleRelVo();
		entity.setKeyword(keyword);
		entity.setRoleId(roleId);
		entity.setUserId(userId);
		return super.listDTOPage(entity);
	}

	/**
	 * 添加
	 *
	 * @param entity
	 * @return
	 */
	@Override
    @Resubmit
	@ApiOperation(value = "分配用户-角色", produces = "application/json;charset=UTF-8")
	@OperationLog(operation = "系统角色权限关联-管理", content = "分配用户-角色")
	@RequiresPermissions("sys:user:role:rel:adds")
	@PostMapping("adds")
	public ResponseEntity adds(@RequestBody List<SysUserRoleRelDto> entity) {
		del(entity);
		return del(entity) ? super.adds(entity) : ResponseEntity.success("保存成功");
	}

	private boolean del(List<SysUserRoleRelDto> entity) {
		if (CollectionUtil.isNotEmpty(entity)) {
			SysUserRoleRelPo sysUserRoleRel = new SysUserRoleRelPo();
			sysUserRoleRel.setUserId(entity.get(0).getUserId());
			sysUserRoleRelService.delete(sysUserRoleRel);
		}
		if (CollectionUtil.isEmpty(entity) || (CollectionUtil.isNotEmpty(entity) && entity.size() == 1 && entity.get(0).getRoleId() == null)) {
			return false;
		} else {
			return true;
		}
	}

}
