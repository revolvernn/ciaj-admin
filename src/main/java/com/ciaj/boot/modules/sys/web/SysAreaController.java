package com.ciaj.boot.modules.sys.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.sys.entity.dto.SysAreaDto;
import com.ciaj.boot.modules.sys.entity.po.SysAreaPo;
import com.ciaj.boot.modules.sys.entity.vo.SysAreaVo;
import com.ciaj.boot.modules.sys.service.SysAreaService;
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
 * @Date: 2019-01-09 14:12:24
 * @Description: www.ciaj.com mvc api
 */
@ResponseBody
@Controller
@RequestMapping("sys/area")
@Api(tags = "系统-区域管理")
public class SysAreaController extends AbstractController<SysAreaPo, SysAreaDto, SysAreaVo> {

	@Autowired
	private SysAreaService sysAreaService;

	/**
	 * 根据ID获取信息
	 *
	 * @param id
	 * @return
	 */
	@Override
    @ApiOperation("根据ID获取区域")
	@ApiImplicitParam(name = "id", value = "区域ID", required = true, dataType = "string", paramType = "path")
	@OperationLog(operation = "系统-区域", content = "根据ID获取区域")
	@RequiresPermissions("sys:area:getById")
	@GetMapping("getById/{id}")
	public ResponseEntity<SysAreaDto> getById(@PathVariable("id") String id) {
		return super.getById(id);
	}

	/**
	 * 列表
	 *
	 * @param keyword
	 * @return
	 */
	@ApiOperation("获取区域列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
			@ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
			@ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "类型", paramType = "query"),
			@ApiImplicitParam(name = "level", value = "级别", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "parentId", value = "父级ID", paramType = "query")
	})
	@OperationLog(operation = "系统-区域", content = "获取区域列表")
	@RequiresPermissions("sys:area:list")
	@GetMapping("list")
	public ResponseEntity<Page<SysAreaDto>> list(String keyword, String type, Integer level, String parentId) {
		SysAreaVo entity = new SysAreaVo();
		entity.setParentId(parentId);
		entity.setKeyword(keyword);
		entity.setType(type);
		entity.setLevel(level);
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
	@ApiOperation(value = "添加区域", produces = "application/json;charset=UTF-8")
	@OperationLog(operation = "系统-区域", content = "添加区域")
	@RequiresPermissions("sys:area:add")
	@PostMapping("add")
	public ResponseEntity add(@RequestBody SysAreaDto entity) {
		checkEntity(entity);
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
	@ApiOperation("更新区域")
	@OperationLog(operation = "系统-区域", content = "更新区域")
	@RequiresPermissions("sys:area:update")
	@PutMapping("update")
	public ResponseEntity update(@RequestBody SysAreaDto entity) {
		checkEntity(entity);
		SysAreaPo p = sysAreaService.selectByPrimaryKey(entity.getId());
		ResponseEntity update = super.updateByVersion(entity, entity.getVersion());
		if (!p.getParentId().equals(entity.getParentId())) {
			updateParentIds(entity);
		}
		return update;
	}

	private void checkEntity(SysAreaDto entity) {
		if (entity.getParentId() == null) {
			entity.setParentId("0");
			entity.setParentIds("0");
			entity.setLevel(1);
		}
		SysAreaPo q = new SysAreaPo();
		q.setCode(entity.getCode());
		q.setDelFlag(DefaultConstant.FLAG_N);
		q.setParentId(entity.getParentId());
		List<SysAreaPo> list = sysAreaService.select(q);
		//同一父级下不能有相同的CODE
		if (CollectionUtil.isNotEmpty(list)) {
			if (entity.getId() == null) {
				throw new BsRException("同一组区域编码不能重复");
			}
			for (SysAreaPo p : list) {
				if (!p.getId().equals(entity.getId())) {
					throw new BsRException("同一组区域编码不能重复");
				}
			}
		}
		q = new SysAreaPo();
		q.setName(entity.getName());
		q.setDelFlag(DefaultConstant.FLAG_N);
		q.setParentId(entity.getParentId());
		list = sysAreaService.select(q);
		//同一父级下不能有相同的CODE
		if (CollectionUtil.isNotEmpty(list)) {
			if (entity.getId() == null) {
				throw new BsRException("同一组区域名称不能重复");
			}
			for (SysAreaPo p : list) {
				if (!p.getId().equals(entity.getId())) {
					throw new BsRException("同一组区域名称不能重复");
				}
			}
		}
	}

	/**
	 * 递归处理下级数据
	 *
	 * @param entity
	 */
	private void updateParentIds(SysAreaDto entity) {
		SysAreaPo query = new SysAreaPo();
		query.setParentId(entity.getId());
		List<SysAreaPo> pos = sysAreaService.select(query);
		if (CollectionUtil.isNotEmpty(pos)) {
			for (SysAreaPo po : pos) {
				po.setParentId(entity.getId());
				po.setLevel(entity.getLevel() + 1);
				po.setParentIds(entity.getParentIds() + "," + entity.getId());
				super.updateByVersion(po, po.getVersion());
				updateParentIds(poToDto(po));
			}
		}
	}

	/**
	 * 软删除
	 *
	 * @param id
	 * @return
	 */
	@Override
    @Resubmit(ParamTypeEnum.url)
	@ApiOperation("根据ID删除区域")
	@ApiImplicitParam(name = "id", value = "区域ID", required = true, dataType = "string", paramType = "path")
	@OperationLog(operation = "系统-区域", content = "根据ID删除区域")
	@RequiresPermissions("sys:area:delFlag")
	@DeleteMapping("/delFlag/{id}")
	public ResponseEntity deleteFlag(@PathVariable("id") String id) {
		SysAreaPo sysAreaPo = sysAreaService.selectByPrimaryKey(id);
		ResponseEntity responseEntity = super.deleteFlagVersion(id, sysAreaPo.getVersion());
		delByParentId(id);
		return responseEntity;
	}


	/**
	 * 递归处理下级数据
	 *
	 * @param parentId
	 */
	private void delByParentId(String parentId) {
		SysAreaPo query = new SysAreaPo();
		query.setParentId(parentId);
		List<SysAreaPo> pos = sysAreaService.select(query);
		if (CollectionUtil.isNotEmpty(pos)) {
			for (SysAreaPo po : pos) {
				super.deleteFlagVersion(po.getId(), po.getVersion());
				delByParentId(po.getId());
			}
		}
	}


}
