package com.ciaj.boot.modules.sys.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.sys.entity.po.SysLogPo;
import com.ciaj.boot.modules.sys.entity.vo.SysLogVo;
import com.ciaj.boot.modules.sys.entity.dto.SysLogDto;
import com.ciaj.boot.modules.sys.service.SysLogService;
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
 * @Date: 2019-04-12 16:26:57
 * @Description: www.ciaj.com mvc api TODO
 */
@Api(tags = "系统日志-管理")
@ResponseBody
@Controller
@RequestMapping("sys/log")
public class SysLogController extends AbstractController<SysLogPo, SysLogDto, SysLogVo> {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 根据ID获取信息
     *
     * @param id
     *
     * @return
     */
    @ApiOperation("根据ID获取系统日志")
    @ApiImplicitParam(name = "id", value = "系统日志ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "系统日志-管理", content = "根据ID获取系统日志")
    @RequiresPermissions("sys:log:getById")
    @GetMapping("/getById/{id}")
    public ResponseEntity<SysLogDto> getById(@PathVariable("id") String id) {
        return super.getById(id);
    }

    /**
     * 列表
     *
     * @return
     */
    @ApiOperation("获取系统日志列表")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
    @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
    @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
    @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
    @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
    @ApiImplicitParam(name = "type", value = "日志类型", paramType = "query"),
    @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query")
    })
    @OperationLog(operation = "系统日志-管理", content = "获取系统日志列表")
    @RequiresPermissions("sys:log:list")
    @GetMapping("/list")
    public ResponseEntity<Page<SysLogDto>> list(String keyword,String type) {
        SysLogVo entity = new SysLogVo();
        entity.setKeyword(keyword);
        entity.setType(type);
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
    @ApiOperation(value = "添加系统日志", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "系统日志-管理", content = "添加系统日志")
    @RequiresPermissions("sys:log:add")
    @PostMapping("add")
    public ResponseEntity add(@RequestBody SysLogDto entity) {
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
    @ApiOperation(value = "更新系统日志", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "系统日志-管理", content = "添加系统日志")
    @RequiresPermissions("sys:log:update")
    @PutMapping("update")
    public ResponseEntity update(@RequestBody SysLogDto entity) {
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
    @ApiOperation("根据ID删除系统日志")
    @ApiImplicitParam(name = "id", value = "系统日志ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "系统日志-管理", content = "根据ID删除系统日志")
    @RequiresPermissions("sys:log:delFlag")
    @DeleteMapping("/delFlag/{id}")
    public ResponseEntity deleteFlag(@PathVariable("id") String id) {
        return super.deleteFlag(id);
    }

}
