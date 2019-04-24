package com.ciaj.boot.modules.sys.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.sys.entity.po.SysUserPo;
import com.ciaj.boot.modules.sys.entity.vo.SysUserVo;
import com.ciaj.boot.modules.sys.entity.dto.SysUserDto;
import com.ciaj.boot.modules.sys.service.SysUserService;
import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.annotation.OperationLog;
import com.ciaj.comm.annotation.Resubmit;
import com.ciaj.comm.constant.ParamTypeEnum;
import com.ciaj.comm.utils.Page;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 17:02:08
 * @Description: www.ciaj.com mvc api TODO
 */
@Api(tags = "系统用户-管理")
@ResponseBody
@Controller
@RequestMapping("sys/user")
public class SysUserController extends AbstractController<SysUserPo, SysUserDto, SysUserVo> {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据ID获取信息
     *
     * @param id
     *
     * @return
     */
    @ApiOperation("根据ID获取系统用户")
    @ApiImplicitParam(name = "id", value = "系统用户ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "系统用户-管理", content = "根据ID获取系统用户")
    @RequiresPermissions("sys:user:getById")
    @GetMapping("/getById/{id}")
    public ResponseEntity<SysUserDto> getById(@PathVariable("id") String id) {
        return super.getById(id);
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
    @ApiImplicitParam(name = "locked", value = "状态", paramType = "query")
    })
    @OperationLog(operation = "系统用户-管理", content = "获取系统用户列表")
    @RequiresPermissions("sys:user:list")
    @GetMapping("/list")
    public ResponseEntity<Page<SysUserDto>> list(String keyword,String locked) {
        SysUserVo entity = new SysUserVo();
        entity.setKeyword(keyword);
        entity.setLocked(locked);
        return super.listDTOPage(entity);
    }

    /**
     * 添加
     *
     * @param entity
     *
     * @return
     */
    @Resubmit
    @ApiOperation(value = "添加系统用户", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "系统用户-管理", content = "添加系统用户")
    @RequiresPermissions("sys:user:add")
    @PostMapping("add")
    public ResponseEntity add(@RequestBody SysUserDto entity) {
        return super.add(entity);
    }

    /**
     * 更新
     *
     * @param entity
     *
     * @return
     */
    @Resubmit
    @ApiOperation(value = "更新区域", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "系统用户-管理", content = "添加系统用户")
    @RequiresPermissions("sys:user:update")
    @PutMapping("update")
    public ResponseEntity update(@RequestBody SysUserDto entity) {
        return super.update(entity);
    }

    /**
     * 软删除
     *
     * @param id
     *
     * @return
     */
    @Resubmit(ParamTypeEnum.url)
    @ApiOperation("根据ID删除系统用户")
    @ApiImplicitParam(name = "id", value = "系统用户ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "系统用户-管理", content = "根据ID删除系统用户")
    @RequiresPermissions("sys:user:delFlag")
    @DeleteMapping("/delFlag/{id}")
    public ResponseEntity deleteFlag(@PathVariable("id") String id) {
        return super.deleteFlag(id);
    }

}
