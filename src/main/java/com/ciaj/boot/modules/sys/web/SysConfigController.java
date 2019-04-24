package com.ciaj.boot.modules.sys.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.sys.entity.dto.SysConfigDto;
import com.ciaj.boot.modules.sys.entity.po.SysConfigPo;
import com.ciaj.boot.modules.sys.entity.vo.SysConfigVo;
import com.ciaj.boot.modules.sys.service.SysConfigService;
import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.annotation.OperationLog;
import com.ciaj.comm.annotation.Resubmit;
import com.ciaj.comm.constant.ParamTypeEnum;
import com.ciaj.comm.utils.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Ciaj.
 * @Date: 2019-02-20 14:55:21
 * @Description: www.ciaj.com mvc api
 */
@ResponseBody
@Controller
@RequestMapping("sys/config")
@Api(tags = "系统-配置管理")
public class SysConfigController extends AbstractController<SysConfigPo, SysConfigDto, SysConfigVo> {
	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * 根据ID获取信息
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation("根据ID获取配置")
	@ApiImplicitParam(name = "id", value = "配置ID", required = true, dataType = "string", paramType = "path")
	@OperationLog(operation = "系统-配置", content = "根据ID获取配置")
	@RequiresPermissions("sys:config:getById")
	@GetMapping("/getById/{id}")
	public ResponseEntity<SysConfigDto> getById(@PathVariable("id") String id) {
		return super.getById(id);
	}

	/**
	 * 列表
	 *
	 * @return
	 */
	@ApiOperation("获取配置列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
			@ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
			@ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "是否启用", paramType = "query")
	})
	@OperationLog(operation = "系统-配置", content = "获取配置列表")
	@RequiresPermissions("sys:config:list")
	@GetMapping("/list")
	public ResponseEntity<Page<SysConfigDto>> list(String keyword, String status) {
		SysConfigVo entity = new SysConfigVo();
		entity.setKeyword(keyword);
		entity.setStatus(status);
		return super.listDTOPage(entity);
	}

	/**
	 * 添加
	 *
	 * @param entity
	 * @return
	 */
	@Resubmit
	@ApiOperation(value = "添加配置")
	@OperationLog(operation = "系统-配置", content = "添加配置")
	@RequiresPermissions("sys:config:add")
	@PostMapping("add")
	public ResponseEntity add(@RequestBody SysConfigDto entity) {
		return super.add(entity);
	}

	/**
	 * 更新
	 *
	 * @param entity
	 * @return
	 */
	@Resubmit
	@ApiOperation("修改配置")
	@OperationLog(operation = "系统-配置", content = "修改配置")
	@RequiresPermissions("sys:config:update")
	@PutMapping("update")
	public ResponseEntity update(@RequestBody SysConfigDto entity) {
		return super.update(entity);
	}

	/**
	 * 软删除
	 *
	 * @param id
	 * @return
	 */
	@Resubmit(ParamTypeEnum.url)
	@ApiOperation("根据ID删除配置")
	@ApiImplicitParam(name = "id", value = "配置ID", required = true, dataType = "string", paramType = "path")
	@OperationLog(operation = "系统-配置", content = "删除配置")
	@RequiresPermissions("sys:config:delFlag")
	@DeleteMapping("/delFlag/{id}")
	public ResponseEntity deleteFlag(@PathVariable("id") String id) {
		return super.deleteFlag(id);
	}

}
