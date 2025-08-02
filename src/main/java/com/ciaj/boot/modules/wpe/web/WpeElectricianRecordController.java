package com.ciaj.boot.modules.wpe.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.wpe.entity.po.WpeElectricianRecordPo;
import com.ciaj.boot.modules.wpe.entity.vo.WpeElectricianRecordVo;
import com.ciaj.boot.modules.wpe.entity.dto.WpeElectricianRecordDto;
import com.ciaj.boot.modules.wpe.service.WpeElectricianRecordService;
import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.annotation.OperationLog;
import com.ciaj.comm.constant.ParamTypeEnum;
import io.swagger.annotations.*;
import com.ciaj.comm.annotation.Resubmit;
import com.ciaj.comm.utils.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: Ciaj.
 * @Date: 2025-07-25 22:28:35
 * @Description: www.ciaj.com gen mvc api
 */
@Api(tags = "水电工程记录-管理")
@ResponseBody
@Controller
@RequestMapping("wpe/electrician/record")
public class WpeElectricianRecordController extends AbstractController<WpeElectricianRecordPo, WpeElectricianRecordDto, WpeElectricianRecordVo> {

    @Autowired
    private WpeElectricianRecordService wpeElectricianRecordService;

    /**
     * 根据ID获取信息
     *
     * @param id
     *
     * @return
     */
    @ApiOperation("根据ID获取水电工程记录")
    @ApiImplicitParam(name = "id", value = "水电工程记录ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "水电工程记录-管理", content = "根据ID获取水电工程记录")
    @RequiresPermissions("wpe:electrician:record:getById")
    @GetMapping("/getById/{id}")
    public ResponseEntity<WpeElectricianRecordDto> getById(@PathVariable("id") String id) {
        WpeElectricianRecordDto wpeElectricianRecordDto = wpeElectricianRecordService.selectById(id);
        return new ResponseEntity<WpeElectricianRecordDto>().put(wpeElectricianRecordDto);
    }

    /**
     * 列表
     *
     * @return
     */
    @ApiOperation("获取水电工程记录列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
        @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
        @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "query"),
        @ApiImplicitParam(name = "projectId", value = "项目ID", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", paramType = "query")
    })
    @OperationLog(operation = "水电工程记录-管理", content = "获取水电工程记录列表")
    @RequiresPermissions("wpe:electrician:record:list")
    @GetMapping("/list")
    public ResponseEntity<Page<WpeElectricianRecordDto>> list(String keyword,String userId,String projectId,String status) {
        WpeElectricianRecordVo entity = new WpeElectricianRecordVo();
        entity.setKeyword(keyword);
        entity.setStatus(status);
        entity.setUserId(userId);
        entity.setProjectId(projectId);
        return super.listDTOPage(entity);
    }


    /**
     * 列表
     *
     * @return
     */
    @ApiOperation("获取水电工程记录列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
        @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
        @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "query"),
        @ApiImplicitParam(name = "projectId", value = "项目ID", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "年月", paramType = "query")
    })
    @OperationLog(operation = "水电工程记录-管理", content = "获取水电工程记录统计列表")
    @RequiresPermissions("wpe:electrician:record:statistics")
    @GetMapping("/statistics")
    public ResponseEntity<Page<Map<String, Object>>> statistics(String keyword, String userId, String projectId, String month) {
        WpeElectricianRecordVo entity = new WpeElectricianRecordVo();
        entity.setKeyword(keyword);
        entity.setUserId(userId);
        entity.setProjectId(projectId);
        entity.setMonth(month);
        entity.setDelFlag("N");
        return new ResponseEntity<>("查询成功").put(wpeElectricianRecordService.statisticsPage(entity));
    }

    /**
     * 添加
     *
     * @param entity
     *
     * @return
     */
    @Resubmit
    @ApiOperation(value = "添加水电工程记录", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "水电工程记录-管理", content = "添加水电工程记录")
    @RequiresPermissions("wpe:electrician:record:add")
    @PostMapping("add")
    public ResponseEntity add(@RequestBody WpeElectricianRecordDto entity) {
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
    @ApiOperation(value = "更新水电工程记录", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "水电工程记录-管理", content = "添加水电工程记录")
    @RequiresPermissions("wpe:electrician:record:update")
    @PutMapping("update")
    public ResponseEntity update(@RequestBody WpeElectricianRecordDto entity) {
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
    @ApiOperation("根据ID删除水电工程记录")
    @ApiImplicitParam(name = "id", value = "水电工程记录ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "水电工程记录-管理", content = "根据ID删除水电工程记录")
    @RequiresPermissions("wpe:electrician:record:delFlag")
    @DeleteMapping("/delFlag/{id}")
    public ResponseEntity deleteFlag(@PathVariable("id") String id) {
        return super.deleteFlag(id);
    }

}
