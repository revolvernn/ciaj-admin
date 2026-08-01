package com.ciaj.boot.modules.my.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.my.entity.po.MyFamilyPo;
import com.ciaj.boot.modules.my.entity.vo.MyFamilyVo;
import com.ciaj.boot.modules.my.entity.dto.MyFamilyDto;
import com.ciaj.boot.modules.my.service.MyFamilyService;
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
 * @Date: 2026-01-14 16:21:01
 * @Description: www.ciaj.com gen mvc api
 */
@Api(tags = "我的家庭-管理")
@ResponseBody
@Controller
@RequestMapping("my/family")
public class MyFamilyController extends AbstractController<MyFamilyPo, MyFamilyDto, MyFamilyVo> {

    @Autowired
    private MyFamilyService myFamilyService;

    /**
     * 根据ID获取信息
     *
     * @param id
     *
     * @return
     */
    @Override
    @ApiOperation("根据ID获取我的家庭")
    @ApiImplicitParam(name = "id", value = "我的家庭ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "我的家庭-管理", content = "根据ID获取我的家庭")
    @RequiresPermissions("my:family:getById")
    @GetMapping("getById/{id}")
    public ResponseEntity<MyFamilyDto> getById(@PathVariable("id") String id) {

        return new ResponseEntity<MyFamilyDto>().put(myFamilyService.selectById(id));
    }

    /**
     * 列表
     *
     * @return
     */
    @ApiOperation("获取我的家庭列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
        @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
        @ApiImplicitParam(name = "areaId", value = "区域ID", paramType = "query"),
        @ApiImplicitParam(name = "enabled", value = "是否可用", paramType = "query")
    })
    @OperationLog(operation = "我的家庭-管理", content = "获取我的家庭列表")
    @RequiresPermissions("my:family:list")
    @GetMapping("list")
    public ResponseEntity<Page<MyFamilyDto>> list(String keyword, String areaId, String enabled) {
        MyFamilyVo entity = new MyFamilyVo();
        entity.setKeyword(keyword);
        entity.setAreaId(areaId);
        entity.setEnabled(enabled);
        return super.listDTOPage(entity);
    }

    /**
     * 列表导出
     *
     * @return
     */
    @ApiOperation("我的家庭列表导出")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
        @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query")
    })
    @OperationLog(operation = "我的家庭-管理", content = "我的家庭列表导出")
    @RequiresPermissions("my:family:list:export")
    @GetMapping("list/export")
    public void listExport(String keyword, HttpServletResponse response, HttpServletRequest request) {
        MyFamilyVo entity = new MyFamilyVo();
        entity.setKeyword(keyword);
        entity.setDelFlag("N");
        //
        Page<MyFamilyDto> page = myFamilyService.selectDTOPage(entity);
        List<MyFamilyDto> data = page.getList();
        //
        new ExcelUtil().build("我的家庭导出",
        new String[]{
            "id","name","code","enabled","areaId","addr","description","createTime","updateTime"
        },
        new String[]{
            "主键","家庭名称","家庭编码","是否可用","家庭区域","家庭详细地址","家庭描述","创建时间","更新时间"
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
    @ApiOperation(value = "添加我的家庭", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "我的家庭-管理", content = "添加我的家庭")
    @RequiresPermissions("my:family:add")
    @PostMapping("add")
    public ResponseEntity add(@RequestBody MyFamilyDto entity) {
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
    @ApiOperation(value = "更新我的家庭", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "我的家庭-管理", content = "添加我的家庭")
    @RequiresPermissions("my:family:update")
    @PutMapping("update")
    public ResponseEntity update(@RequestBody MyFamilyDto entity) {
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
    @ApiOperation("根据ID删除我的家庭")
    @ApiImplicitParam(name = "id", value = "我的家庭ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "我的家庭-管理", content = "根据ID删除我的家庭")
    @RequiresPermissions("my:family:delFlag")
    @DeleteMapping("delFlag/{id}")
    public ResponseEntity deleteFlag(@PathVariable("id") String id) {
        return super.deleteFlag(id);
    }
}
