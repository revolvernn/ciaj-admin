package com.ciaj.boot.modules.sys.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.oss.cloud.CloudStorageConfig;
import com.ciaj.boot.modules.sys.entity.dto.SysOssDto;
import com.ciaj.boot.modules.sys.entity.po.SysConfigPo;
import com.ciaj.boot.modules.sys.entity.po.SysOssPo;
import com.ciaj.boot.modules.sys.entity.vo.SysOssVo;
import com.ciaj.boot.modules.sys.service.SysConfigService;
import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.annotation.OperationLog;
import com.ciaj.comm.annotation.Resubmit;
import com.ciaj.comm.constant.DefaultConfigConstant;
import com.ciaj.comm.constant.DefaultConstant;
import com.ciaj.comm.constant.ParamTypeEnum;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.validate.*;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 16:35:33
 * @Description: www.ciaj.com mvc api TODO
 */
@Api(tags = "系统OSS-管理")
@ResponseBody
@Controller
@RequestMapping("sys/oss")
public class SysOssController extends AbstractController<SysOssPo, SysOssDto, SysOssVo> {
	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * 添加
	 *
	 * @param config
	 * @return
	 */
	@Resubmit
	@ApiOperation(value = "保存OSS云存储配置", produces = "application/json;charset=UTF-8")
	@OperationLog(operation = "系统-OSS", content = "保存OSS云存储配置")
	@RequiresPermissions("sys:oss:config:save")
	@PostMapping("config/save")
	public ResponseEntity save(@RequestBody CloudStorageConfig config) {

		//校验类型
		ValidatorUtils.validateEntity(config);
		if (config.getType().equals(DefaultConfigConstant.OSSCloud.QINIUCLOUD.getValue())) {
			//校验七牛数据
			ValidatorUtils.validateEntity(config, QiniuGroup.class);
		} else if (config.getType().equals(DefaultConfigConstant.OSSCloud.ALIYUN.getValue())) {
			//校验阿里云数据
			ValidatorUtils.validateEntity(config, AliyunGroup.class);
		} else if (config.getType().equals(DefaultConfigConstant.OSSCloud.QCLOUD.getValue())) {
			//校验腾讯云数据
			ValidatorUtils.validateEntity(config, QcloudGroup.class);
		} else if (config.getType().equals(DefaultConfigConstant.OSSCloud.LOCAL.getValue())) {
			ValidatorUtils.validateEntity(config, LocalGroup.class);
		}

		SysConfigPo query = new SysConfigPo();
		query.setConfigKey(DefaultConfigConstant.CLOUD_STORAGE_CONFIG_KEY);
		query.setDelFlag(DefaultConstant.FLAG_N);
		List<SysConfigPo> select = sysConfigService.select(query);
		if (CollectionUtils.isNotEmpty(select)) {
			for (SysConfigPo sysConfig : select) {
				sysConfig.setStatus(config.getStatus());
				sysConfig.setConfigValue(new Gson().toJson(config));
				sysConfigService.updateByPrimaryKey(sysConfig);
			}
		} else {
			SysConfigPo entity = new SysConfigPo();
			entity.setConfigKey(DefaultConfigConstant.CLOUD_STORAGE_CONFIG_KEY);
			entity.setStatus(config.getStatus());
			entity.setConfigValue(new Gson().toJson(config));
			sysConfigService.insert(entity);
		}
		return ResponseEntity.success("保存成功");
	}

	/**
	 * 获取OSS云存储配置
	 *
	 * @return
	 */
	@ApiOperation("获取OSS云存储配置")
	@OperationLog(operation = "系统-OSS", content = "获取OSS云存储配置")
	@RequiresPermissions("sys:oss:getConfig")
	@GetMapping("getConfig")
	public ResponseEntity<ResponseEntity> getOssConfig() {
		final CloudStorageConfig configObject = sysConfigService.getConfigObject(DefaultConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);
		return new ResponseEntity<CloudStorageConfig>().put(configObject);
	}

	/**
	 * 根据ID获取信息
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation("根据ID获取系统OSS")
	@ApiImplicitParam(name = "id", value = "系统OSSID", required = true, dataType = "string", paramType = "path")
	@OperationLog(operation = "系统OSS-管理", content = "根据ID获取系统OSS")
	@RequiresPermissions("sys:oss:getById")
	@GetMapping("/getById/{id}")
	public ResponseEntity<SysOssDto> getById(@PathVariable("id") String id) {
		return super.getById(id);
	}

	/**
	 * 列表
	 *
	 * @return
	 */
	@ApiOperation("获取系统OSS列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
			@ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
			@ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "文件类型", paramType = "query"),
			@ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query")
	})
	@OperationLog(operation = "系统OSS-管理", content = "获取系统OSS列表")
	@RequiresPermissions("sys:oss:list")
	@GetMapping("/list")
	public ResponseEntity<Page<SysOssDto>> list(String type,String keyword) {
		SysOssVo entity = new SysOssVo();
		entity.setKeyword(keyword);
		entity.setType(type);
		return super.listDTOPage(entity);
	}

	/**
	 * 添加
	 *
	 * @param entity
	 * @return
	 */
	@Resubmit
	@ApiOperation(value = "添加系统OSS", produces = "application/json;charset=UTF-8")
	@OperationLog(operation = "系统OSS-管理", content = "添加系统OSS")
	@RequiresPermissions("sys:oss:add")
	@PostMapping("add")
	public ResponseEntity add(@RequestBody SysOssDto entity) {
		return super.add(entity);
	}

	/**
	 * 更新
	 *
	 * @param entity
	 * @return
	 */
	@Resubmit
	@ApiOperation(value = "更新系统OSS", produces = "application/json;charset=UTF-8")
	@OperationLog(operation = "系统OSS-管理", content = "添加系统OSS")
	@RequiresPermissions("sys:oss:update")
	@PutMapping("update")
	public ResponseEntity update(@RequestBody SysOssDto entity) {
		return super.update(entity);
	}

	/**
	 * 软删除
	 *
	 * @param id
	 * @return
	 */
	@Resubmit(ParamTypeEnum.url)
	@ApiOperation("根据ID删除系统OSS")
	@ApiImplicitParam(name = "id", value = "系统OSSID", required = true, dataType = "string", paramType = "path")
	@OperationLog(operation = "系统OSS-管理", content = "根据ID删除系统OSS")
	@RequiresPermissions("sys:oss:delFlag")
	@DeleteMapping("/delFlag/{id}")
	public ResponseEntity deleteFlag(@PathVariable("id") String id) {
		return super.deleteFlag(id);
	}

}
