package com.ciaj.boot.modules.sys.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.sys.entity.dto.SysMenuDto;
import com.ciaj.boot.modules.sys.entity.po.SysMenuPo;
import com.ciaj.boot.modules.sys.entity.vo.SysMenuVo;
import com.ciaj.boot.modules.sys.service.SysMenuService;
import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.annotation.OperationLog;
import com.ciaj.comm.annotation.Resubmit;
import com.ciaj.comm.constant.DefaultConstant;
import com.ciaj.comm.constant.ParamTypeEnum;
import com.ciaj.comm.exception.BsRException;
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
 * @Date: 2019-04-12 16:28:44
 * @Description: www.ciaj.com mvc api
 */
@Api(tags = "系统菜单-管理")
@ResponseBody
@Controller
@RequestMapping("sys/menu")
public class SysMenuController extends AbstractController<SysMenuPo, SysMenuDto, SysMenuVo> {

	@Autowired
	private SysMenuService sysMenuService;

	/**
	 * 根据ID获取信息
	 *
	 * @param id
	 * @return
	 */
	@Override
    @ApiOperation("根据ID获取系统菜单")
	@ApiImplicitParam(name = "id", value = "系统菜单ID", required = true, dataType = "string", paramType = "path")
	@OperationLog(operation = "系统菜单-管理", content = "根据ID获取系统菜单")
	@RequiresPermissions("sys:menu:getById")
	@GetMapping("getById/{id}")
	public ResponseEntity<SysMenuDto> getById(@PathVariable("id") String id) {
		return super.getById(id);
	}

	/**
	 * 列表
	 *
	 * @return
	 */
	@ApiOperation("获取系统菜单列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
			@ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
			@ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
			@ApiImplicitParam(name = "parentName", value = "父级名称", paramType = "query"),
			@ApiImplicitParam(name = "parentId", value = "父级ID", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "菜单类型", paramType = "query"),
			@ApiImplicitParam(name = "enabled", value = "是否启用", paramType = "query")
	})
	@OperationLog(operation = "系统菜单-管理", content = "获取系统菜单列表")
	@RequiresPermissions("sys:menu:list")
	@GetMapping("list")
	public ResponseEntity<Page<SysMenuDto>> list(String keyword, String parentName, String parentId, String type, String enabled) {
		SysMenuVo entity = new SysMenuVo();
		entity.setKeyword(keyword);
		entity.setParentId(parentId);
		entity.setType(type);
		entity.setEnabled(enabled);
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
	@ApiOperation(value = "添加系统菜单", produces = "application/json;charset=UTF-8")
	@OperationLog(operation = "系统菜单-管理", content = "添加系统菜单")
	@RequiresPermissions("sys:menu:add")
	@PostMapping("add")
	public ResponseEntity add(@RequestBody SysMenuDto entity) {
		checkMenu(entity);
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
	@ApiOperation(value = "更新系统菜单", produces = "application/json;charset=UTF-8")
	@OperationLog(operation = "系统菜单-管理", content = "添加系统菜单")
	@RequiresPermissions("sys:menu:update")
	@PutMapping("update")
	public ResponseEntity update(@RequestBody SysMenuDto entity) {
		checkMenu(entity);
		SysMenuPo p = sysMenuService.selectByPrimaryKey(entity.getId());
		//
		ResponseEntity update = super.updateByVersion(entity, entity.getVersion());
		//
		if (!p.getParentId().equals(entity.getParentId())) {
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
	@Override
	@Resubmit(ParamTypeEnum.url)
	@ApiOperation("根据ID删除系统菜单")
	@ApiImplicitParam(name = "id", value = "系统菜单ID", required = true, dataType = "string", paramType = "path")
	@OperationLog(operation = "系统菜单-管理", content = "根据ID删除系统菜单")
	@RequiresPermissions("sys:menu:delFlag")
	@DeleteMapping("delFlag/{id}")
	public ResponseEntity deleteFlag(@PathVariable("id") String id) {
		SysMenuPo sysMenuPo = sysMenuService.selectByPrimaryKey(id);
		ResponseEntity responseEntity = super.deleteFlagVersion(id, sysMenuPo.getVersion());
		delByParentId(id);
		return responseEntity;
	}

	/**
	 * 递归处理下级
	 *
	 * @param parentId
	 */
	private void delByParentId(String parentId) {
		SysMenuPo query = new SysMenuPo();
		query.setParentId(parentId);
		List<SysMenuPo> pos = sysMenuService.select(query);
		if (CollectionUtil.isNotEmpty(pos)) {
			for (SysMenuPo po : pos) {
				super.deleteFlagVersion(po.getId(), po.getVersion());
				delByParentId(po.getId());
			}
		}
	}


	private void checkMenu(SysMenuDto entity) {
		if (entity.getParentId() == null) {
			entity.setParentId("0");
			entity.setParentIds("0");
		}
		SysMenuVo q = new SysMenuVo();
		q.setDelFlag(DefaultConstant.FLAG_N);
		q.setName(entity.getName());
		q.setParentId(entity.getParentId());
		List<SysMenuPo> sysMenus = sysMenuService.select(q);
		//同一父级下不能有相同的CODE
		if (CollectionUtil.isNotEmpty(sysMenus)) {
			if (entity.getId() == null) throw new BsRException("同一组菜单名称不能重复");
			for (SysMenuPo sysMenu : sysMenus) {
				if (!sysMenu.getId().equals(entity.getId())) {
					throw new BsRException("同一组菜单名称不能重复");
				}
			}
		}
	}


	/**
	 * 递归处理下级数据
	 *
	 * @param entity
	 */
	private void updateParentIds(SysMenuDto entity) {
		SysMenuPo query = new SysMenuPo();
		query.setParentId(entity.getId());
		List<SysMenuPo> pos = sysMenuService.select(query);
		if (CollectionUtil.isNotEmpty(pos)) {
			for (SysMenuPo po : pos) {
				po.setParentId(entity.getId());
				po.setParentIds(entity.getParentIds() + "," + entity.getId());
				super.updateByVersion(po, po.getVersion());
				updateParentIds(poToDto(po));
			}
		}
	}

}
