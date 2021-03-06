package com.ciaj.boot.modules.sys.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.sys.entity.dto.SysRolePermissionRelDto;
import com.ciaj.boot.modules.sys.entity.po.SysRolePermissionRelPo;
import com.ciaj.boot.modules.sys.entity.vo.SysRolePermissionRelVo;
import com.ciaj.boot.modules.sys.service.SysRolePermissionRelService;
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
 * @Date: 2019-04-12 16:54:59
 * @Description: www.ciaj.com mvc api
 */
@Api(tags = "系统角色权限关联-管理")
@ResponseBody
@Controller
@RequestMapping("sys/role/permission/rel")
public class SysRolePermissionRelController extends AbstractController<SysRolePermissionRelPo, SysRolePermissionRelDto, SysRolePermissionRelVo> {

	@Autowired
	private SysRolePermissionRelService sysRolePermissionRelService;

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
			@ApiImplicitParam(name = "permissionId", value = "权限ID", paramType = "query")
	})
	@OperationLog(operation = "系统角色权限关联-管理", content = "获取系统角色权限关联列表")
	@RequiresPermissions("sys:role:permission:rel:list")
	@GetMapping("/list")
	public ResponseEntity<Page<SysRolePermissionRelDto>> list(String keyword,String roleId,String permissionId) {
		SysRolePermissionRelVo entity = new SysRolePermissionRelVo();
		entity.setKeyword(keyword);
		entity.setRoleId(roleId);
		entity.setPermissionId(permissionId);
		return super.listDTOPage(entity);
	}

	/**
	 * 添加
	 *
	 * @param entity
	 * @return
	 */
	@Resubmit
	@ApiOperation(value = "分配角色-权限", produces = "application/json;charset=UTF-8")
	@OperationLog(operation = "系统角色权限关联-管理", content = "分配角色-权限")
	@RequiresPermissions("sys:role:permission:rel:adds")
	@PostMapping("adds")
	public ResponseEntity adds(@RequestBody List<SysRolePermissionRelDto> entity) {
		del(entity);
		return del(entity) ? super.adds(entity) : ResponseEntity.success("保存成功");
	}

	private Boolean del(List<SysRolePermissionRelDto> entity) {
		if (CollectionUtil.isNotEmpty(entity)) {
			SysRolePermissionRelPo sysRolePermissionRel = new SysRolePermissionRelPo();
			sysRolePermissionRel.setRoleId(entity.get(0).getRoleId());
			sysRolePermissionRelService.delete(sysRolePermissionRel);
		}
		if (CollectionUtil.isEmpty(entity) || (CollectionUtil.isNotEmpty(entity) && entity.size() == 1 && entity.get(0).getPermissionId() == null)) {
			return false;
		} else {
			return true;
		}
	}

}
