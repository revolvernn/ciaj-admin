package com.ciaj.boot.modules.my.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.my.entity.po.MyFamilyDebitPo;
import com.ciaj.boot.modules.my.entity.vo.MyFamilyDebitVo;
import com.ciaj.boot.modules.my.entity.dto.MyFamilyDebitDto;
import com.ciaj.boot.modules.my.service.MyFamilyDebitService;
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
 * @Date: 2026-08-04 19:44:41
 * @Description: www.ciaj.com gen mvc api
 */
@Api(tags = "家庭借记-管理")
@ResponseBody
@Controller
@RequestMapping("my/family/debit")
public class MyFamilyDebitController extends AbstractController<MyFamilyDebitPo, MyFamilyDebitDto, MyFamilyDebitVo> {

    @Autowired
    private MyFamilyDebitService myFamilyDebitService;

    /**
     * 根据ID获取信息
     *
     * @param id
     *
     * @return
     */
    @Override
    @ApiOperation("根据ID获取家庭借记")
    @ApiImplicitParam(name = "id", value = "家庭借记ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "家庭借记-管理", content = "根据ID获取家庭借记")
    @RequiresPermissions("my:family:debit:getById")
    @GetMapping("getById/{id}")
    public ResponseEntity<MyFamilyDebitDto> getById(@PathVariable("id") String id) {
        return new ResponseEntity<MyFamilyDebitDto>().put(myFamilyDebitService.selectById(id));
    }

    /**
     * 列表
     *
     * @return
     */
    @ApiOperation("获取家庭借记列表")
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
    @OperationLog(operation = "家庭借记-管理", content = "获取家庭借记列表")
    @RequiresPermissions("my:family:debit:list")
    @GetMapping("list")
    public ResponseEntity<Page<MyFamilyDebitDto>> list(String keyword,String userId,String type) {
        MyFamilyDebitVo entity = new MyFamilyDebitVo();
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
    @ApiOperation("家庭借记列表导出")
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
    @OperationLog(operation = "家庭借记-管理", content = "家庭借记列表导出")
    @RequiresPermissions("my:family:debit:list:export")
    @GetMapping("list/export")
    public void listExport(String keyword,String userId,String type, HttpServletResponse response, HttpServletRequest request) {
        MyFamilyDebitVo entity = new MyFamilyDebitVo();
        entity.setKeyword(keyword);
        entity.setDelFlag("N");
        entity.setType(type);
        entity.setUserId(userId);
        //
        Page<MyFamilyDebitDto> page = myFamilyDebitService.selectDTOPage(entity);
        List<MyFamilyDebitDto> data = page.getList();
        //
        new ExcelUtil().build("家庭借记导出",
                new String[]{
                        "userId","user.nickname","type","day","money","addr","remark","createTime","updateTime"
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
    @ApiOperation(value = "添加家庭借记", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "家庭借记-管理", content = "添加家庭借记")
    @RequiresPermissions("my:family:debit:add")
    @PostMapping("add")
    public ResponseEntity add(@RequestBody MyFamilyDebitDto entity) {
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
    @ApiOperation(value = "更新家庭借记", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "家庭借记-管理", content = "更新家庭借记")
    @RequiresPermissions("my:family:debit:update")
    @PutMapping("update")
    public ResponseEntity update(@RequestBody MyFamilyDebitDto entity) {
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
    @ApiOperation("根据ID删除家庭借记")
    @ApiImplicitParam(name = "id", value = "家庭借记ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "家庭借记-管理", content = "根据ID删除家庭借记")
    @RequiresPermissions("my:family:debit:delFlag")
    @DeleteMapping("delFlag/{id}")
    public ResponseEntity deleteFlag(@PathVariable("id") String id) {
        return super.deleteFlag(id);
    }
}
