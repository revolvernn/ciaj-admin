package com.ciaj.boot.modules.sys.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.sys.entity.po.DemoTablePo;
import com.ciaj.boot.modules.sys.entity.vo.DemoTableVo;
import com.ciaj.boot.modules.sys.entity.dto.DemoTableDto;
import com.ciaj.boot.modules.sys.service.DemoTableService;
import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.annotation.OperationLog;
import io.swagger.annotations.*;
import com.ciaj.comm.utils.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 17:54:57
 * @Description: www.ciaj.com mvc api TODO
 */
@Api(tags = "系统模板-管理")
@ResponseBody
@Controller
@RequestMapping("demo/table")
public class DemoTableController extends AbstractController<DemoTablePo, DemoTableDto, DemoTableVo> {

    @Autowired
    private DemoTableService demoTableService;

    /**
     * 根据ID获取信息
     *
     * @param id
     *
     * @return
     */
    @ApiOperation("根据ID获取系统模板")
    @ApiImplicitParam(name = "id", value = "系统模板ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "系统模板-管理", content = "根据ID获取系统模板")
    @RequiresPermissions("demo:table:getById")
    @GetMapping("/getById/{id}")
    public ResponseEntity<DemoTableDto> getById(@PathVariable("id") String id) {
        return super.getById(id);
    }

    /**
     * 列表
     *
     * @return
     */
    @ApiOperation("获取系统模板列表")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
    @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
    @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
    @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
    @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
    @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query")
    })
    @OperationLog(operation = "系统模板-管理", content = "获取系统模板列表")
    @RequiresPermissions("demo:table:list")
    @GetMapping("/list")
    public ResponseEntity<Page<DemoTableDto>> list(String keyword) {
        DemoTableVo entity = new DemoTableVo();
        entity.setKeyword(keyword);
        return super.listDTOPage(entity);
    }

    /**
     * 添加
     *
     * @param entity
     *
     * @return
     */
    @ApiOperation(value = "添加系统模板", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "系统模板-管理", content = "添加系统模板")
    @RequiresPermissions("demo:table:add")
    @PostMapping("add")
    public ResponseEntity add(@RequestBody DemoTableDto entity) {
        return super.add(entity);
    }

    /**
     * 更新
     *
     * @param entity
     *
     * @return
     */
    @ApiOperation(value = "更新系统模板", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "系统模板-管理", content = "添加系统模板")
    @RequiresPermissions("demo:table:update")
    @PutMapping("update")
    public ResponseEntity update(@RequestBody DemoTableDto entity) {
        return super.update(entity);
    }

    /**
     * 软删除
     *
     * @param id
     *
     * @return
     */
    @ApiOperation("根据ID删除系统模板")
    @ApiImplicitParam(name = "id", value = "系统模板ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "系统模板-管理", content = "根据ID删除系统模板")
    @RequiresPermissions("demo:table:delFlag")
    @DeleteMapping("/delFlag/{id}")
    public ResponseEntity deleteFlag(@PathVariable("id") String id) {
        return super.deleteFlag(id);
    }

}
