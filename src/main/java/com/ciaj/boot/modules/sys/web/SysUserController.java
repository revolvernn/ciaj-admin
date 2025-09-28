package com.ciaj.boot.modules.sys.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.sys.entity.dto.SysUserDto;
import com.ciaj.boot.modules.sys.entity.po.SysDeptPo;
import com.ciaj.boot.modules.sys.entity.po.SysUserPo;
import com.ciaj.boot.modules.sys.entity.vo.SysUserVo;
import com.ciaj.boot.modules.sys.service.SysDeptService;
import com.ciaj.boot.modules.sys.service.SysUserService;
import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.annotation.OperationLog;
import com.ciaj.comm.annotation.Resubmit;
import com.ciaj.comm.constant.ParamTypeEnum;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 17:02:08
 * @Description: www.ciaj.com mvc api
 */
@Api(tags = "系统用户-管理")
@ResponseBody
@Controller
@RequestMapping("sys/user")
public class SysUserController extends AbstractController<SysUserPo, SysUserDto, SysUserVo> {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysDeptService sysDeptService;

	/**
	 * 根据ID获取信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	@ApiOperation("根据ID获取系统用户")
	@ApiImplicitParam(name = "id", value = "系统用户ID", required = true, dataType = "string", paramType = "path")
	@OperationLog(operation = "系统用户-管理", content = "根据ID获取系统用户")
	@RequiresPermissions("sys:user:getById")
	@GetMapping("getById/{id}")
	public ResponseEntity<SysUserDto> getById(@PathVariable("id") String id) {
		return new ResponseEntity<SysUserDto>().put(sysUserService.selectById(id));
	}

	/**
	 * 列表
	 *
	 * @return
	 */
	@ApiOperation("获取系统用户列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
			@ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
			@ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "id", paramType = "query"),
			@ApiImplicitParam(name = "locked", value = "状态", paramType = "query"),
			@ApiImplicitParam(name = "roleCode", value = "角色编码", paramType = "query")
	})
	@OperationLog(operation = "系统用户-管理", content = "获取系统用户列表")
	@RequiresPermissions("sys:user:list")
	@GetMapping("/list")
	public ResponseEntity<Page<SysUserDto>> list(String keyword, String id, String locked, String roleCode) {
		SysUserVo entity = new SysUserVo();
		entity.setKeyword(keyword);
		entity.setLocked(locked);
		entity.setId(id);
		entity.setRoleCode(roleCode);
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
	@ApiOperation(value = "添加系统用户", produces = "application/json;charset=UTF-8")
	@OperationLog(operation = "系统用户-管理", content = "添加系统用户")
	@RequiresPermissions("sys:user:add")
	@PostMapping("add")
	public ResponseEntity add(@RequestBody SysUserDto entity) {
		initDept(entity);
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
	@ApiOperation(value = "更新用户", produces = "application/json;charset=UTF-8")
	@OperationLog(operation = "系统用户-管理", content = "添加系统用户")
	@RequiresPermissions("sys:user:update")
	@PutMapping("update")
	public ResponseEntity update(@RequestBody SysUserDto entity) {
		initDept(entity);
		return super.updateByVersion(entity, entity.getVersion());
	}

	/**
	 * 处理用户组织信息
	 *
	 * @param entity
	 */
	private void initDept(SysUserDto entity) {
		if (StringUtil.isNotBlank(entity.getDeptId())) {
			SysDeptPo sysDeptPo = sysDeptService.selectByPrimaryKey(entity.getDeptId());
			entity.setDeptName(sysDeptPo.getName());
			entity.setDeptIds(sysDeptPo.getParentIds());
			List<SysDeptPo> sysDeptPos = sysDeptService.selectListByKeys(sysDeptPo.getParentIds().split(","));
			List<String> list = new ArrayList<>();
			for (SysDeptPo deptPo : sysDeptPos) {
				list.add(deptPo.getName());
			}
			list.add(sysDeptPo.getName());
			entity.setDeptNames(StringUtil.getJoinString("-", list));
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
	@ApiOperation("根据ID删除系统用户")
	@ApiImplicitParam(name = "id", value = "系统用户ID", required = true, dataType = "string", paramType = "path")
	@OperationLog(operation = "系统用户-管理", content = "根据ID删除系统用户")
	@RequiresPermissions("sys:user:delFlag")
	@DeleteMapping("delFlag/{id}")
	public ResponseEntity deleteFlag(@PathVariable("id") String id) {
		SysUserPo sysUserPo = sysUserService.selectByPrimaryKey(id);
		return super.deleteFlagVersion(id, sysUserPo.getVersion());
	}

}
