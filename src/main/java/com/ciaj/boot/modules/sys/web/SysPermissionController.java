package com.ciaj.boot.modules.sys.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.sys.entity.dto.SysPermissionDto;
import com.ciaj.boot.modules.sys.entity.po.SysPermissionPo;
import com.ciaj.boot.modules.sys.entity.po.SysRolePermissionRelPo;
import com.ciaj.boot.modules.sys.entity.vo.SysPermissionVo;
import com.ciaj.boot.modules.sys.service.SysPermissionService;
import com.ciaj.boot.modules.sys.service.SysRolePermissionRelService;
import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.annotation.OperationLog;
import com.ciaj.comm.annotation.Resubmit;
import com.ciaj.comm.constant.DefaultConstant;
import com.ciaj.comm.constant.ParamTypeEnum;
import com.ciaj.comm.exception.BsRException;
import com.ciaj.comm.utils.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 16:41:56
 * @Description: www.ciaj.com mvc api TODO
 */
@Api(tags = "系统权限-管理")
@ResponseBody
@Controller
@RequestMapping("sys/permission")
public class SysPermissionController extends AbstractController<SysPermissionPo, SysPermissionDto, SysPermissionVo> {

	@Autowired
	private SysPermissionService sysPermissionService;
	@Autowired
	private SysRolePermissionRelService sysRolePermissionRelService;

	/**
	 * 根据ID获取信息
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation("根据ID获取系统权限")
	@ApiImplicitParam(name = "id", value = "系统权限ID", required = true, dataType = "string", paramType = "path")
	@OperationLog(operation = "系统权限-管理", content = "根据ID获取系统权限")
	@RequiresPermissions("sys:permission:getById")
	@GetMapping("/getById/{id}")
	public ResponseEntity<SysPermissionDto> getById(@PathVariable("id") String id) {
		return super.getById(id);
	}

	/**
	 * 列表
	 *
	 * @return
	 */
	@ApiOperation("获取系统权限列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
			@ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
			@ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
			@ApiImplicitParam(name = "parentId", value = "父级ID", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "权限类型", paramType = "query")
	})
	@OperationLog(operation = "系统权限-管理", content = "获取系统权限列表")
	@RequiresPermissions("sys:permission:list")
	@GetMapping("/list")
	public ResponseEntity<Page<SysPermissionDto>> list(String keyword, String type,String parentId) {
		SysPermissionVo entity = new SysPermissionVo();
		entity.setKeyword(keyword);
		entity.setType(type);
		entity.setParentId(parentId);
		return super.listDTOPage(entity);
	}

	/**
	 * 添加
	 *
	 * @param entity
	 * @return
	 */
	@Resubmit
	@ApiOperation(value = "添加系统权限", produces = "application/json;charset=UTF-8")
	@OperationLog(operation = "系统权限-管理", content = "添加系统权限")
	@RequiresPermissions("sys:permission:add")
	@PostMapping("add")
	public ResponseEntity add(@RequestBody SysPermissionDto entity) {
		checkPermission(entity);
		return super.add(entity);
	}

	/**
	 * 更新
	 *
	 * @param entity
	 * @return
	 */
	@Resubmit
	@ApiOperation(value = "更新系统权限", produces = "application/json;charset=UTF-8")
	@OperationLog(operation = "系统权限-管理", content = "添加系统权限")
	@RequiresPermissions("sys:permission:update")
	@PutMapping("update")
	public ResponseEntity update(@RequestBody SysPermissionDto entity) {
		checkPermission(entity);
		SysPermissionPo sysPermission = sysPermissionService.selectByPrimaryKey(entity.getId());

		ResponseEntity update = super.updateByVersion(entity, entity.getVersion());

		if (!sysPermission.getParentId().equals(entity.getParentId())) {
			updateParentIds(entity);
		}
		return update;
	}

	/**
	 * 软删除
	 *
	 * @param id
	 * @return
	 */
	@Resubmit(ParamTypeEnum.url)
	@ApiOperation("根据ID删除系统权限")
	@ApiImplicitParam(name = "id", value = "系统权限ID", required = true, dataType = "string", paramType = "path")
	@OperationLog(operation = "系统权限-管理", content = "根据ID删除系统权限")
	@RequiresPermissions("sys:permission:delFlag")
	@DeleteMapping("/delFlag/{id}")
	public ResponseEntity deleteFlag(@PathVariable("id") String id) {
		SysRolePermissionRelPo query = new SysRolePermissionRelPo();
		List<SysRolePermissionRelPo> select = sysRolePermissionRelService.select(query);
		if (CollectionUtils.isNotEmpty(select)) {
			throw new BsRException("权限已绑定角色，不能删除。");
		}
		SysPermissionPo sysPermissionPo = sysPermissionService.selectByPrimaryKey(id);
		ResponseEntity responseEntity = super.deleteFlagVersion(id, sysPermissionPo.getVersion());
		delByParentId(id);
		return responseEntity;
	}

	/**
	 * 递归处理下级
	 *
	 * @param parentId
	 */
	private void delByParentId(String parentId) {
		SysPermissionPo query = new SysPermissionPo();
		query.setParentId(parentId);
		List<SysPermissionPo> pos = sysPermissionService.select(query);
		if (CollectionUtils.isNotEmpty(pos)) {
			for (SysPermissionPo po : pos) {
				super.deleteFlagVersion(po.getId(), po.getVersion());
				delByParentId(po.getId());
			}
		}
	}


	private void checkPermission(SysPermissionDto entity) {
		if (entity.getParentId() == null) {
			entity.setParentId("0");
			entity.setParentIds("0");
		}
		SysPermissionVo q = new SysPermissionVo();
		q.setDelFlag(DefaultConstant.FLAG_N);
		q.setName(entity.getName());
		q.setParentId(entity.getParentId());
		List<SysPermissionPo> sysPermissions = sysPermissionService.select(q);
		//同一父级下不能有相同的NAME
		if (CollectionUtils.isNotEmpty(sysPermissions)) {
			if (entity.getId() == null) throw new BsRException("同一组权限名称不能重复");
			for (SysPermissionPo sysPermission : sysPermissions) {
				if (!sysPermission.getId().equals(entity.getId())) {
					throw new BsRException("同一组权限名称不能重复");
				}
			}
		}
		if (StringUtils.isBlank(entity.getPermissionCode())) return;
		q = new SysPermissionVo();
		q.setDelFlag(DefaultConstant.FLAG_N);
		q.setPermissionCode(entity.getPermissionCode());
		q.setParentId(entity.getParentId());
		sysPermissions = sysPermissionService.select(q);
		//同一父级下不能有相同的NAME
		if (CollectionUtils.isNotEmpty(sysPermissions)) {
			if (entity.getId() == null) throw new BsRException("同一组权限码不能重复");
			for (SysPermissionPo sysPermission : sysPermissions) {
				if (!sysPermission.getId().equals(entity.getId())) {
					throw new BsRException("同一组权限码不能重复");
				}
			}
		}

	}

	/**
	 * 处理下级的父级
	 *
	 * @param entity
	 */
	private void updateParentIds(SysPermissionDto entity) {

		SysPermissionPo query = new SysPermissionPo();
		query.setParentId(entity.getId());
		List<SysPermissionPo> sysPermissions = sysPermissionService.select(query);
		if (CollectionUtils.isNotEmpty(sysPermissions)) {
			for (SysPermissionPo po : sysPermissions) {
				po.setParentId(entity.getId());
				po.setParentIds(entity.getParentIds() + "," + entity.getId());
				super.updateByVersion(po, po.getVersion());
				updateParentIds(poToDto(po));
			}
		}
	}

}
