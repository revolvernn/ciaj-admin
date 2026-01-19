package com.ciaj.boot.modules.sys.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.sys.entity.dto.SysDictDto;
import com.ciaj.boot.modules.sys.entity.po.SysDictPo;
import com.ciaj.boot.modules.sys.entity.vo.SysDictVo;
import com.ciaj.boot.modules.sys.service.SysDictService;
import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.annotation.OperationLog;
import com.ciaj.comm.annotation.Resubmit;
import com.ciaj.comm.constant.DefaultConstant;
import com.ciaj.comm.constant.ParamTypeEnum;
import com.ciaj.comm.exception.BsRException;
import com.ciaj.comm.utils.AssertUtil;
import com.ciaj.comm.utils.CollectionUtil;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.Safes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 16:17:42
 * @Description: www.ciaj.com mvc api
 */
@Api(tags = "系统字典-管理")
@ResponseBody
@Controller
@RequestMapping("sys/dict")
public class SysDictController extends AbstractController<SysDictPo, SysDictDto, SysDictVo> {

    @Autowired
    private SysDictService sysDictService;

    /**
     * 根据ID获取信息
     *
     * @param id
     * @return
     */
    @Override
    @ApiOperation("根据ID获取系统字典")
    @ApiImplicitParam(name = "id", value = "系统字典ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "系统字典-管理", content = "根据ID获取系统字典", isInsert = false)
    //@RequiresPermissions("sys:dict:getById")
    @RequiresUser
    @GetMapping("getById/{id}")
    public ResponseEntity<SysDictDto> getById(@PathVariable("id") String id) {
        return super.getById(id);
    }

    /**
     * 列表
     *
     * @return
     */
    @ApiOperation("获取系统字典列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
            @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
            @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "字典类型", paramType = "query"),
            @ApiImplicitParam(name = "parentId", value = "父级ID", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "字典名称", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "字典CODE", paramType = "query")
    })
    @OperationLog(operation = "系统字典-管理", content = "获取系统字典列表", isInsert = false)
    @RequiresUser
    //@RequiresPermissions("sys:dict:list")
    @GetMapping("list")
    public ResponseEntity<Page<SysDictDto>> list(String keyword, String type, String parentId, String name, String code) {
        SysDictVo entity = new SysDictVo();
        entity.setKeyword(keyword);
        entity.setType(type);
        entity.setParentId(parentId);
        entity.setName(name);
        entity.setCode(code);
        return super.listDTOPage(entity);
    }

    /**
     * 添加
     *
     * @param entity
     * @return
     */
    @Override
    @Resubmit
    @ApiOperation(value = "添加系统字典", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "系统字典-管理", content = "添加系统字典")
    @RequiresPermissions("sys:dict:add")
    @PostMapping("add")
    public ResponseEntity add(@RequestBody SysDictDto entity) {
        checkDict(entity);
        return super.add(entity);
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    @Override
    @Resubmit
    @ApiOperation(value = "更新系统字典", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "系统字典-管理", content = "添加系统字典")
    @RequiresPermissions("sys:dict:update")
    @PutMapping("update")
    public ResponseEntity update(@RequestBody SysDictDto entity) {
        checkDict(entity);
        SysDictPo p = sysDictService.selectByPrimaryKey(entity.getId());
        ResponseEntity update = super.update(entity);
        if (!p.getParentId().equals(entity.getParentId())) {
            updateParentIds(entity);
        }
        return update;
    }

    /**
     * 软删除
     *
     * @param id
     * @return
     */
    @Override
    @Resubmit(ParamTypeEnum.url)
    @ApiOperation("根据ID删除系统字典")
    @ApiImplicitParam(name = "id", value = "系统字典ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "系统字典-管理", content = "根据ID删除系统字典")
    @RequiresPermissions("sys:dict:delFlag")
    @DeleteMapping("delFlag/{id}")
    public ResponseEntity deleteFlag(@PathVariable("id") String id) {
        SysDictPo sysDictPo = sysDictService.selectByPrimaryKey(id);
        ResponseEntity responseEntity = super.deleteFlagVersion(id, sysDictPo.getVersion());
        delByParentId(id);
        return responseEntity;
    }

    /**
     * 递归处理下级字典
     *
     * @param parentId
     */
    private void delByParentId(String parentId) {
        SysDictPo query = new SysDictPo();
        query.setParentId(parentId);
        List<SysDictPo> pos = sysDictService.select(query);
        Safes.of(pos).forEach(po -> {
            super.deleteFlagVersion(po.getId(), po.getVersion());
            delByParentId(po.getId());
        });
    }

    /**
     * 添加更新前检查字典合法
     *
     * @param entity
     */
    private void checkDict(SysDictDto entity) {
        if (entity.getParentId() == null) {
            entity.setParentId("0");
            entity.setParentIds("0");
            entity.setLevel(1);
        }
        SysDictVo q = new SysDictVo();
        q.setCode(entity.getCode());
        q.setDelFlag(DefaultConstant.FLAG_N);
        q.setParentId(entity.getParentId());
        List<SysDictPo> sysDicts = sysDictService.select(q);
        //同一父级下不能有相同的CODE
        if (CollectionUtil.isNotEmpty(sysDicts)) {
            AssertUtil.notNull(entity.getId(), "同一组字典编码不能重复");
            boolean b = sysDicts.stream().anyMatch(f -> !f.getId().equals(entity.getId()));
            AssertUtil.isFalse(b, "同一组字典编码不能重复");
        }
        q = new SysDictVo();
        q.setName(entity.getName());
        q.setDelFlag(DefaultConstant.FLAG_N);
        q.setParentId(entity.getParentId());
        sysDicts = sysDictService.select(q);
        //同一父级下不能有相同的CODE
        if (CollectionUtil.isNotEmpty(sysDicts)) {
            AssertUtil.notNull(entity.getId(), "同一组字典编码不能重复");
            boolean b = sysDicts.stream().anyMatch(f -> !f.getId().equals(entity.getId()));
            AssertUtil.isFalse(b, "同一组字典编码不能重复");
        }
        q = new SysDictVo();
        q.setDelFlag(DefaultConstant.FLAG_N);
        q.setType(entity.getType());
        sysDicts = sysDictService.select(q);
        //不同的父级不能有相同的类型
        boolean b = Safes.of(sysDicts).anyMatch(sysDict -> (entity.getId() == null && !sysDict.getParentId().equals(entity.getParentId()))
                || (entity.getId() != null && !sysDict.getId().equals(entity.getId()) && !sysDict.getParentId().equals(entity.getParentId())));
        AssertUtil.isFalse(b, "字典类型已经在其他层级存在");
    }

    /**
     * 递归处理下级数据
     *
     * @param entity
     */
    private void updateParentIds(SysDictDto entity) {
        SysDictPo query = new SysDictPo();
        query.setParentId(entity.getId());
        List<SysDictPo> pos = sysDictService.select(query);
        Safes.of(pos).forEach(po -> {
            po.setParentId(entity.getId());
            po.setLevel(entity.getLevel() + 1);
            po.setParentIds(entity.getParentIds() + "," + entity.getId());
            super.updateByVersion(po, po.getVersion());
            updateParentIds(poToDto(po));
        });
    }

}
