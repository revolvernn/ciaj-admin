package com.ciaj.boot.modules.sys.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.sys.entity.dto.SysDeptDto;
import com.ciaj.boot.modules.sys.entity.po.SysDeptPo;
import com.ciaj.boot.modules.sys.entity.vo.SysDeptVo;
import com.ciaj.boot.modules.sys.service.SysDeptService;
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

;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 16:09:47
 * @Description: www.ciaj.com mvc api TODO
 */
@Api(tags = "系统部门-管理")
@ResponseBody
@Controller
@RequestMapping("sys/dept")
public class SysDeptController extends AbstractController<SysDeptPo, SysDeptDto, SysDeptVo> {

	@Autowired
	private SysDeptService sysDeptService;

	/**
	 * 根据ID获取信息
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation("根据ID获取系统部门")
	@ApiImplicitParam(name = "id", value = "系统部门ID", required = true, dataType = "string", paramType = "path")
	@OperationLog(operation = "系统部门-管理", content = "根据ID获取系统部门")
	@RequiresPermissions("sys:dept:getById")
	@GetMapping("/getById/{id}")
	public ResponseEntity<SysDeptDto> getById(@PathVariable("id") String id) {
		return new ResponseEntity<SysDeptDto>().put(sysDeptService.selectById(id));
	}

	/**
	 * 列表
	 *
	 * @return
	 */
	@ApiOperation("获取系统部门列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
			@ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
			@ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
			@ApiImplicitParam(name = "parentId", value = "部门父级ID", paramType = "query"),
			@ApiImplicitParam(name = "areaId", value = "区域ID", paramType = "query")
	})
	@OperationLog(operation = "系统部门-管理", content = "获取系统部门列表")
	@RequiresPermissions("sys:dept:list")
	@GetMapping("/list")
	public ResponseEntity<Page<SysDeptDto>> list(String keyword, String parentId, String areaId) {
		SysDeptVo entity = new SysDeptVo();
		entity.setKeyword(keyword);
		entity.setParentId(parentId);
		entity.setAreaId(areaId);
		return super.listDTOPage(entity);
	}

	/**
	 * 添加
	 *
	 * @param entity
	 * @return
	 */
	@Resubmit
	@ApiOperation(value = "添加系统部门", produces = "application/json;charset=UTF-8")
	@OperationLog(operation = "系统部门-管理", content = "添加系统部门")
	@RequiresPermissions("sys:dept:add")
	@PostMapping("add")
	public ResponseEntity add(@RequestBody SysDeptDto entity) {
		checkDept(entity);
		return super.add(entity);
	}

	/**
	 * 更新
	 *
	 * @param entity
	 * @return
	 */
	@Resubmit
	@ApiOperation(value = "更新系统部门", produces = "application/json;charset=UTF-8")
	@OperationLog(operation = "系统部门-管理", content = "添加系统部门")
	@RequiresPermissions("sys:dept:update")
	@PutMapping("update")
	public ResponseEntity update(@RequestBody SysDeptDto entity) {
		checkDept(entity);
		SysDeptPo p = sysDeptService.selectByPrimaryKey(entity.getId());
		ResponseEntity responseEntity = super.updateByVersion(entity, entity.getVersion());
		if (!p.getParentId().equals(entity.getParentId())) {
			updateParentIds(entity);
		}
		return responseEntity;
	}

	/**
	 * 软删除
	 *
	 * @param id
	 * @return
	 */
	@Resubmit(ParamTypeEnum.url)
	@ApiOperation("根据ID删除系统部门")
	@ApiImplicitParam(name = "id", value = "系统部门ID", required = true, dataType = "string", paramType = "path")
	@OperationLog(operation = "系统部门-管理", content = "根据ID删除系统部门")
	@RequiresPermissions("sys:dept:delFlag")
	@DeleteMapping("/delFlag/{id}")
	public ResponseEntity deleteFlag(@PathVariable("id") String id) {
		SysDeptPo sysDeptPo = sysDeptService.selectByPrimaryKey(id);
		ResponseEntity responseEntity = super.deleteFlagVersion(id, sysDeptPo.getVersion());
		delByParentId(id);
		return responseEntity;
	}

	private void updateParentIds(SysDeptDto entity) {
		SysDeptPo query = new SysDeptPo();
		query.setParentId(entity.getId());
		List<SysDeptPo> pos = sysDeptService.select(query);
		if (CollectionUtil.isNotEmpty(pos)) {
			for (SysDeptPo po : pos) {
				po.setParentId(entity.getId());
				po.setLevel(entity.getLevel() + 1);
				po.setParentIds(entity.getParentIds() + "," + entity.getId());
				super.updateByVersion(po, po.getVersion());
				updateParentIds(poToDto(po));
			}
		}
	}

	private void delByParentId(String parentId) {
		SysDeptPo query = new SysDeptPo();
		query.setParentId(parentId);
		List<SysDeptPo> pos = sysDeptService.select(query);
		if (CollectionUtil.isNotEmpty(pos)) {
			for (SysDeptPo po : pos) {
				super.deleteFlagVersion(po.getId(), po.getVersion());
				delByParentId(po.getId());
			}
		}
	}

	private void checkDept(SysDeptDto entity) {
		if (entity.getParentId() == null) {
			entity.setParentId("0");
			entity.setParentIds("0");
			entity.setLevel(1);
		}
		SysDeptVo q = new SysDeptVo();
		q.setCode(entity.getCode());
		q.setDelFlag(DefaultConstant.FLAG_N);
		q.setName(entity.getName());
		q.setDelFlag(DefaultConstant.FLAG_N);
		q.setParentId(entity.getParentId());
		List<SysDeptPo> list = sysDeptService.select(q);
		//同一父级下不能有相同的CODE
		if (CollectionUtil.isNotEmpty(list)) {
			if (entity.getId() == null) throw new BsRException("同一组部门名称不能重复");
			for (SysDeptPo s : list) {
				if (!s.getId().equals(entity.getId())) {
					throw new BsRException("同一组部门名称不能重复");
				}
			}
		}
	}

}
