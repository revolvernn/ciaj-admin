package com.ciaj.boot.modules.wpe.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.wpe.entity.po.WpeProjectPo;
import com.ciaj.boot.modules.wpe.entity.vo.WpeProjectVo;
import com.ciaj.boot.modules.wpe.entity.dto.WpeProjectDto;
import com.ciaj.boot.modules.wpe.service.WpeProjectService;
import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.annotation.OperationLog;
import com.ciaj.comm.constant.ParamTypeEnum;
import com.ciaj.comm.utils.ExcelUtil;
import io.swagger.annotations.*;
import com.ciaj.comm.annotation.Resubmit;
import com.ciaj.comm.utils.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2025-07-27 09:31:59
 * @Description: www.ciaj.com gen mvc api
 */
@Api(tags = "水电工程项目-管理")
@ResponseBody
@Controller
@RequestMapping("wpe/project")
public class WpeProjectController extends AbstractController<WpeProjectPo, WpeProjectDto, WpeProjectVo> {

    @Autowired
    private WpeProjectService wpeProjectService;

    /**
     * 根据ID获取信息
     *
     * @param id
     *
     * @return
     */
    @Override
    @ApiOperation("根据ID获取水电工程项目")
    @ApiImplicitParam(name = "id", value = "水电工程项目ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "水电工程项目-管理", content = "根据ID获取水电工程项目")
    @RequiresPermissions("wpe:project:getById")
    @GetMapping("getById/{id}")
    public ResponseEntity<WpeProjectDto> getById(@PathVariable("id") String id) {
        return super.getById(id);
    }

    /**
     * 列表
     *
     * @return
     */
    @ApiOperation("获取水电工程项目列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
        @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
        @ApiImplicitParam(name = "houseType", value = "项目户型", paramType = "query"),
        @ApiImplicitParam(name = "decorationType", value = "装修类型", paramType = "query"),
        @ApiImplicitParam(name = "projectId", value = "项目ID", paramType = "query")
    })
    @OperationLog(operation = "水电工程项目-管理", content = "获取水电工程项目列表")
    @RequiresPermissions("wpe:project:list")
    @GetMapping("list")
    public ResponseEntity<Page<WpeProjectDto>> list(String keyword, String houseType, String decorationType, String projectId) {
        WpeProjectVo entity = new WpeProjectVo();
        entity.setId(projectId);
        entity.setHouseType(houseType);
        entity.setDecorationType(decorationType);
        entity.setKeyword(keyword);
        return super.listDTOPage(entity);
    }
    /**
     * 列表
     *
     * @return
     */
    @ApiOperation("获取水电工程项目列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
        @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
        @ApiImplicitParam(name = "houseType", value = "项目户型", paramType = "query"),
        @ApiImplicitParam(name = "decorationType", value = "装修类型", paramType = "query"),
        @ApiImplicitParam(name = "projectId", value = "项目ID", paramType = "query")
    })
    @OperationLog(operation = "水电工程项目-管理", content = "获取水电工程项目列表")
    @RequiresPermissions("wpe:project:list:export")
    @GetMapping("list/export")
    public void listExport(String keyword, String houseType, String decorationType, String projectId, HttpServletResponse response, HttpServletRequest request) {
        WpeProjectVo entity = new WpeProjectVo();
        entity.setId(projectId);
        entity.setHouseType(houseType);
        entity.setDecorationType(decorationType);
        entity.setKeyword(keyword);
        Page<WpeProjectDto> page = wpeProjectService.selectDTOPage(entity);
        List<WpeProjectDto> data = page.getList();

        new ExcelUtil().build("水电工程项目导出",
                new String[]{
                        "id","projectName","addr","remark","houseType","decorationType","createTime","updateTime"
                },
                new String[]{
                        "主键","工程项目名称","地址","备注","项目户型","装修类型","创建时间","更新时间"
                }, data).exportExcel(request, response);
    }

    /**
     * 添加
     *
     * @param entity
     *
     * @return
     */
    @Override
    @Resubmit
    @ApiOperation(value = "添加水电工程项目", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "水电工程项目-管理", content = "添加水电工程项目")
    @RequiresPermissions("wpe:project:add")
    @PostMapping("add")
    public ResponseEntity add(@RequestBody @Valid WpeProjectDto entity) {
        return super.add(entity);
    }

    /**
     * 更新
     *
     * @param entity
     *
     * @return
     */
    @Override
    @Resubmit
    @ApiOperation(value = "更新水电工程项目", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "水电工程项目-管理", content = "添加水电工程项目")
    @RequiresPermissions("wpe:project:update")
    @PutMapping("update")
    public ResponseEntity update(@RequestBody @Valid WpeProjectDto entity) {
        return super.update(entity);
    }

    /**
     * 软删除
     *
     * @param id
     *
     * @return
     */
    @Override
    @Resubmit(ParamTypeEnum.url)
    @ApiOperation("根据ID删除水电工程项目")
    @ApiImplicitParam(name = "id", value = "水电工程项目ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "水电工程项目-管理", content = "根据ID删除水电工程项目")
    @RequiresPermissions("wpe:project:delFlag")
    @DeleteMapping("delFlag/{id}")
    public ResponseEntity deleteFlag(@PathVariable("id") String id) {
        return super.deleteFlag(id);
    }

}
