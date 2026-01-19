package com.ciaj.boot.modules.sys.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.sys.entity.dto.SysRoleDto;
import com.ciaj.boot.modules.sys.entity.po.SysRolePermissionRelPo;
import com.ciaj.boot.modules.sys.entity.po.SysRolePo;
import com.ciaj.boot.modules.sys.entity.po.SysUserRoleRelPo;
import com.ciaj.boot.modules.sys.entity.vo.SysRoleVo;
import com.ciaj.boot.modules.sys.service.SysRolePermissionRelService;
import com.ciaj.boot.modules.sys.service.SysRoleService;
import com.ciaj.boot.modules.sys.service.SysUserRoleRelService;
import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.annotation.OperationLog;
import com.ciaj.comm.annotation.Resubmit;
import com.ciaj.comm.constant.ParamTypeEnum;
import com.ciaj.comm.exception.BsRException;
import com.ciaj.comm.utils.AssertUtil;
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
 * @Date: 2019-04-12 16:48:09
 * @Description: www.ciaj.com mvc api
 */
@Api(tags = "系统角色-管理")
@ResponseBody
@Controller
@RequestMapping("sys/role")
public class SysRoleController extends AbstractController<SysRolePo, SysRoleDto, SysRoleVo> {

	@Autowired
	private SysRolePermissionRelService sysRolePermissionRelService;
	@Autowired
	private SysUserRoleRelService sysUserRoleRelService;
	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 根据ID获取信息
	 *
	 * @param id
	 * @return
	 */
	@Override
    @ApiOperation("根据ID获取系统角色")
	@ApiImplicitParam(name = "id", value = "系统角色ID", required = true, dataType = "string", paramType = "path")
	@OperationLog(operation = "系统角色-管理", content = "根据ID获取系统角色")
	@RequiresPermissions("sys:role:getById")
	@GetMapping("getById/{id}")
	public ResponseEntity<SysRoleDto> getById(@PathVariable("id") String id) {
		return super.getById(id);
	}

	/**
	 * 列表
	 *
	 * @return
	 */
	@ApiOperation("获取系统角色列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
			@ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
			@ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "角色类型", paramType = "query"),
			@ApiImplicitParam(name = "available", value = "是否可用", paramType = "query")
	})
	@OperationLog(operation = "系统角色-管理", content = "获取系统角色列表")
	@RequiresPermissions("sys:role:list")
	@GetMapping("list")
	public ResponseEntity<Page<SysRoleDto>> list(String keyword, String type, String available) {
		SysRoleVo entity = new SysRoleVo();
		entity.setKeyword(keyword);
		entity.setType(type);
		entity.setAvailable(available);
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
	@ApiOperation(value = "添加系统角色", produces = "application/json;charset=UTF-8")
	@OperationLog(operation = "系统角色-管理", content = "添加系统角色")
	@RequiresPermissions("sys:role:add")
	@PostMapping("add")
	public ResponseEntity add(@RequestBody SysRoleDto entity) {
		return super.add(entity);
	}

	/**
	 * 更新
	 *
	 * @param entity
	 * @return
	 */
	@Override
	@Resubmit
	@ApiOperation(value = "更新系统角色", produces = "application/json;charset=UTF-8")
	@OperationLog(operation = "系统角色-管理", content = "添加系统角色")
	@RequiresPermissions("sys:role:update")
	@PutMapping("update")
	public ResponseEntity update(@RequestBody SysRoleDto entity) {
		return super.updateByVersion(entity, entity.getVersion());
	}

	/**
	 * 软删除
	 *
	 * @param id
	 * @return
	 */
	@Override
	@Resubmit(ParamTypeEnum.url)
	@ApiOperation("根据ID删除系统角色")
	@ApiImplicitParam(name = "id", value = "系统角色ID", required = true, dataType = "string", paramType = "path")
	@OperationLog(operation = "系统角色-管理", content = "根据ID删除系统角色")
	@RequiresPermissions("sys:role:delFlag")
	@DeleteMapping("delFlag/{id}")
	public ResponseEntity deleteFlag(@PathVariable("id") String id) {
		SysUserRoleRelPo query = new SysUserRoleRelPo();
		query.setRoleId(id);
		List<SysUserRoleRelPo> select = sysUserRoleRelService.select(query);
		AssertUtil.isEmpty(select,"角色已绑定用户，不能删除。");

		SysRolePo sysRolePo = sysRoleService.selectByPrimaryKey(id);
		return super.deleteFlagVersion(id, sysRolePo.getVersion());
	}

	/**
	 * 清空角色关联用户及权限数据
	 *
	 * @param id
	 * @return
	 */
	@Resubmit(ParamTypeEnum.url)
	@ApiOperation("根据ID清空角色关联用户及权限数据")
	@ApiImplicitParam(name = "id", value = "系统角色ID", required = true, dataType = "string", paramType = "path")
	@OperationLog(operation = "系统角色-管理", content = "根据ID清空角色关联用户及权限数据")
	@RequiresPermissions("sys:role:clearRoleRel")
	@DeleteMapping("clearRoleRel/{id}")
	public ResponseEntity clearRoleRel(@PathVariable("id") String id) {
		SysUserRoleRelPo userRoleRel = new SysUserRoleRelPo();
		userRoleRel.setRoleId(id);
		sysUserRoleRelService.delete(userRoleRel);
		SysRolePermissionRelPo rolepermissionRel = new SysRolePermissionRelPo();
		rolepermissionRel.setRoleId(id);
		sysRolePermissionRelService.delete(rolepermissionRel);
		return ResponseEntity.success("操作成功");
	}

}
