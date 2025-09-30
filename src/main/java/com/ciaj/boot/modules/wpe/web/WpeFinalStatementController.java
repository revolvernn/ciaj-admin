package com.ciaj.boot.modules.wpe.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.wpe.entity.po.WpeFinalStatementPo;
import com.ciaj.boot.modules.wpe.entity.vo.WpeFinalStatementVo;
import com.ciaj.boot.modules.wpe.entity.dto.WpeFinalStatementDto;
import com.ciaj.boot.modules.wpe.service.WpeFinalStatementService;
import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.annotation.OperationLog;
import com.ciaj.comm.constant.DefaultConstant;
import com.ciaj.comm.constant.ParamTypeEnum;
import io.swagger.annotations.*;
import com.ciaj.comm.annotation.Resubmit;
import com.ciaj.comm.utils.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ciaj.comm.utils.ExcelUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: Ciaj.
 * @Date: 2025-09-01 16:06:42
 * @Description: www.ciaj.com gen mvc api
 */
@Api(tags = "水电工结算单-管理")
@ResponseBody
@Controller
@RequestMapping("wpe/final/statement")
public class WpeFinalStatementController extends AbstractController<WpeFinalStatementPo, WpeFinalStatementDto, WpeFinalStatementVo> {

    @Autowired
    private WpeFinalStatementService wpeFinalStatementService;

    /**
     * 根据ID获取信息
     *
     * @param id
     *
     * @return
     */
    @Override
    @ApiOperation("根据ID获取水电工结算单")
    @ApiImplicitParam(name = "id", value = "水电工结算单ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "水电工结算单-管理", content = "根据ID获取水电工结算单")
    @RequiresPermissions("wpe:final:statement:getById")
    @GetMapping("getById/{id}")
    public ResponseEntity<WpeFinalStatementDto> getById(@PathVariable("id") String id) {
        WpeFinalStatementDto wpeFinalStatementDto = wpeFinalStatementService.selectById(id);
        return new ResponseEntity<WpeFinalStatementDto>("查询成功").put(wpeFinalStatementDto);
    }

    /**
     * 列表
     *
     * @return
     */
    @ApiOperation("获取水电工结算单列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
        @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
        @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "query")
    })
    @OperationLog(operation = "水电工结算单-管理", content = "获取水电工结算单列表")
    @RequiresPermissions("wpe:final:statement:list")
    @GetMapping("list")
    public ResponseEntity<Page<WpeFinalStatementDto>> list(String keyword, String userId) {
        WpeFinalStatementVo entity = new WpeFinalStatementVo();
        entity.setKeyword(keyword);
        entity.setUserId(userId);
        return super.listDTOPage(entity);
    }
    /**
     * 列表
     *
     * @return
     */
    @ApiOperation("获取水电工结算单统计列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "query")
    })
    @OperationLog(operation = "水电工结算单-管理", content = "获取水电工结算单统计列表")
    @RequiresPermissions("wpe:final:statement:stat")
    @GetMapping("stat")
    public ResponseEntity<List<Map<String, BigDecimal>>> stat(String keyword, String userId) {
        WpeFinalStatementVo entity = new WpeFinalStatementVo();
        entity.setKeyword(keyword);
        entity.setUserId(userId);
        entity.setDelFlag(DefaultConstant.FLAG_N);
        return new ResponseEntity<>("查询成功").put(wpeFinalStatementService.stat(entity));
    }

    /**
     * 列表导出
     *
     * @return
     */
    @ApiOperation("水电工结算单列表导出")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
        @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
        @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "query")
    })
    @OperationLog(operation = "水电工结算单-管理", content = "水电工结算单列表导出")
    @RequiresPermissions("wpe:final:statement:list:export")
    @GetMapping("list/export")
    public void listExport(String keyword, String userId, HttpServletResponse response, HttpServletRequest request) {
        WpeFinalStatementVo entity = new WpeFinalStatementVo();
        entity.setKeyword(keyword);
        entity.setUserId(userId);
        entity.setDelFlag("N");
        //
        Page<WpeFinalStatementDto> page = wpeFinalStatementService.selectDTOPage(entity);
        List<WpeFinalStatementDto> data = page.getList();
        //
        new ExcelUtil().build("水电工结算单导出",
        new String[]{
            "id","userId","day","money","remark","createTime","updateTime"
        },
        new String[]{
            "主键","用户ID","结算日","款项金额","备注","创建时间","更新时间"
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
    @ApiOperation(value = "添加水电工结算单", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "水电工结算单-管理", content = "添加水电工结算单")
    @RequiresPermissions("wpe:final:statement:add")
    @PostMapping("add")
    public ResponseEntity add(@RequestBody @Validated WpeFinalStatementDto entity) {
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
    @ApiOperation(value = "更新水电工结算单", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "水电工结算单-管理", content = "添加水电工结算单")
    @RequiresPermissions("wpe:final:statement:update")
    @PutMapping("update")
    public ResponseEntity update(@RequestBody @Validated WpeFinalStatementDto entity) {
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
    @ApiOperation("根据ID删除水电工结算单")
    @ApiImplicitParam(name = "id", value = "水电工结算单ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "水电工结算单-管理", content = "根据ID删除水电工结算单")
    @RequiresPermissions("wpe:final:statement:delFlag")
    @DeleteMapping("delFlag/{id}")
    public ResponseEntity deleteFlag(@PathVariable("id") String id) {
        return super.deleteFlag(id);
    }
}
