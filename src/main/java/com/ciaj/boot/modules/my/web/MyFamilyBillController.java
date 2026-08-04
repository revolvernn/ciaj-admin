package com.ciaj.boot.modules.my.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.my.entity.po.MyFamilyBillPo;
import com.ciaj.boot.modules.my.entity.vo.MyFamilyBillVo;
import com.ciaj.boot.modules.my.entity.dto.MyFamilyBillDto;
import com.ciaj.boot.modules.my.service.MyFamilyBillService;
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
import com.ciaj.comm.utils.ExcelUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2026-07-13 22:34:02
 * @Description: www.ciaj.com gen mvc api
 */
@Api(tags = "家庭账单-管理")
@ResponseBody
@Controller
@RequestMapping("my/family/bill")
public class MyFamilyBillController extends AbstractController<MyFamilyBillPo, MyFamilyBillDto, MyFamilyBillVo> {

    @Autowired
    private MyFamilyBillService myFamilyBillService;

    /**
     * 根据ID获取信息
     *
     * @param id
     *
     * @return
     */
    @Override
    @ApiOperation("根据ID获取家庭账单")
    @ApiImplicitParam(name = "id", value = "家庭账单ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "家庭账单-管理", content = "根据ID获取家庭账单")
    @RequiresPermissions("my:family:bill:getById")
    @GetMapping("getById/{id}")
    public ResponseEntity<MyFamilyBillDto> getById(@PathVariable("id") String id) {
        return new ResponseEntity<MyFamilyBillDto>("查询成功").put(myFamilyBillService.selectById(id));
    }

    /**
     * 列表
     *
     * @return
     */
    @ApiOperation("获取家庭账单列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
        @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query")
    })
    @OperationLog(operation = "家庭账单-管理", content = "获取家庭账单列表")
    @RequiresPermissions("my:family:bill:list")
    @GetMapping("list")
    public ResponseEntity<Page<MyFamilyBillDto>> list(String keyword,String userId,String type) {
        MyFamilyBillVo entity = new MyFamilyBillVo();
        entity.setKeyword(keyword);
        entity.setType(type);
        entity.setUserId(userId);
        return super.listMultiTablePage(entity);
    }

    /**
     * 列表导出
     *
     * @return
     */
    @ApiOperation("家庭账单列表导出")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
        @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
        @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "query"),
        @ApiImplicitParam(name = "type", value = "类型字典", paramType = "query")
    })
    @OperationLog(operation = "家庭账单-管理", content = "家庭账单列表导出")
    @RequiresPermissions("my:family:bill:list:export")
    @GetMapping("list/export")
    public void listExport(String keyword,String userId,String type, HttpServletResponse response, HttpServletRequest request) {
        MyFamilyBillVo entity = new MyFamilyBillVo();
        entity.setKeyword(keyword);
        entity.setDelFlag("N");
        entity.setType(type);
        entity.setUserId(userId);

        Page<MyFamilyBillDto> page = myFamilyBillService.selectDTOListMultiTablePage(entity);
        List<MyFamilyBillDto> data = page.getList();
        //
        new ExcelUtil().build("家庭账单导出",
        new String[]{
            "userId","user.nickname","dict.name","day","money","addr","remark","createTime","updateTime"
        },
        new String[]{
            "用户ID","用户名","类型","日期","款项","地址","备注","创建时间","更新时间"
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
    @ApiOperation(value = "添加家庭账单", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "家庭账单-管理", content = "添加家庭账单")
    @RequiresPermissions("my:family:bill:add")
    @PostMapping("add")
    public ResponseEntity add(@RequestBody MyFamilyBillDto entity) {
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
    @ApiOperation(value = "更新家庭账单", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "家庭账单-管理", content = "更新家庭账单")
    @RequiresPermissions("my:family:bill:update")
    @PutMapping("update")
    public ResponseEntity update(@RequestBody MyFamilyBillDto entity) {
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
    @ApiOperation("根据ID删除家庭账单")
    @ApiImplicitParam(name = "id", value = "家庭账单ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "家庭账单-管理", content = "根据ID删除家庭账单")
    @RequiresPermissions("my:family:bill:delFlag")
    @DeleteMapping("delFlag/{id}")
    public ResponseEntity deleteFlag(@PathVariable("id") String id) {
        return super.deleteFlag(id);
    }
}
