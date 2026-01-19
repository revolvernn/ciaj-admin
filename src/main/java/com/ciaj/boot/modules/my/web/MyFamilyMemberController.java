package com.ciaj.boot.modules.my.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.my.entity.po.MyFamilyMemberPo;
import com.ciaj.boot.modules.my.entity.vo.MyFamilyMemberVo;
import com.ciaj.boot.modules.my.entity.dto.MyFamilyMemberDto;
import com.ciaj.boot.modules.my.service.MyFamilyMemberService;
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
 * @Date: 2026-01-14 16:48:58
 * @Description: www.ciaj.com gen mvc api
 */
@Api(tags = "家庭成员-管理")
@ResponseBody
@Controller
@RequestMapping("my/family/member")
public class MyFamilyMemberController extends AbstractController<MyFamilyMemberPo, MyFamilyMemberDto, MyFamilyMemberVo> {

    @Autowired
    private MyFamilyMemberService myFamilyMemberService;

    /**
     * 根据ID获取信息
     *
     * @param id
     *
     * @return
     */
    @Override
    @ApiOperation("根据ID获取家庭成员")
    @ApiImplicitParam(name = "id", value = "家庭成员ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "家庭成员-管理", content = "根据ID获取家庭成员")
    @RequiresPermissions("my:family:member:getById")
    @GetMapping("getById/{id}")
    public ResponseEntity<MyFamilyMemberDto> getById(@PathVariable("id") String id) {
        return super.getById(id);
    }

    /**
     * 列表
     *
     * @return
     */
    @ApiOperation("获取家庭成员列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
        @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query")
    })
    @OperationLog(operation = "家庭成员-管理", content = "获取家庭成员列表")
    @RequiresPermissions("my:family:member:list")
    @GetMapping("list")
    public ResponseEntity<Page<MyFamilyMemberDto>> list(String keyword) {
        MyFamilyMemberVo entity = new MyFamilyMemberVo();
        entity.setKeyword(keyword);
        return super.listDTOPage(entity);
    }

    /**
     * 列表导出
     *
     * @return
     */
    @ApiOperation("家庭成员列表导出")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
        @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query")
    })
    @OperationLog(operation = "家庭成员-管理", content = "家庭成员列表导出")
    @RequiresPermissions("my:family:member:list:export")
    @GetMapping("list/export")
    public void listExport(String keyword, HttpServletResponse response, HttpServletRequest request) {
        MyFamilyMemberVo entity = new MyFamilyMemberVo();
        entity.setKeyword(keyword);
        entity.setDelFlag("N");
        //
        Page<MyFamilyMemberDto> page = myFamilyMemberService.selectDTOPage(entity);
        List<MyFamilyMemberDto> data = page.getList();
        //
        new ExcelUtil().build("家庭成员导出",
        new String[]{
            "id","familyId","userId","type","createAt","createTime","updateAt","updateTime","delFlag","version"
        },
        new String[]{
            "主键","家庭ID","用户ID","成员类型","创建人","创建时间","更新人","更新时间","删除标记","版本号0为不可修改，1+可修改"
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
    @ApiOperation(value = "添加家庭成员", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "家庭成员-管理", content = "添加家庭成员")
    @RequiresPermissions("my:family:member:add")
    @PostMapping("add")
    public ResponseEntity add(@RequestBody MyFamilyMemberDto entity) {
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
    @ApiOperation(value = "更新家庭成员", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "家庭成员-管理", content = "添加家庭成员")
    @RequiresPermissions("my:family:member:update")
    @PutMapping("update")
    public ResponseEntity update(@RequestBody MyFamilyMemberDto entity) {
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
    @ApiOperation("根据ID删除家庭成员")
    @ApiImplicitParam(name = "id", value = "家庭成员ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "家庭成员-管理", content = "根据ID删除家庭成员")
    @RequiresPermissions("my:family:member:delFlag")
    @DeleteMapping("delFlag/{id}")
    public ResponseEntity deleteFlag(@PathVariable("id") String id) {
        return super.deleteFlag(id);
    }
}
