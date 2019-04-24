package ${package};

import com.ciaj.base.AbstractController;
import ${poPackage}.${tableClass.shortClassName}Po;
import ${voPo}.${tableClass.shortClassName}Vo;
import ${dtoPo}.${tableClass.shortClassName}Dto;
import ${servicePackage}.${tableClass.shortClassName}Service;
import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.annotation.OperationLog;
import com.ciaj.comm.constant.ParamType;
import io.swagger.annotations.*;
import com.ciaj.comm.annotation.Resubmit;
import com.ciaj.comm.utils.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

<#assign dateTime = .now>
/**
 * @Author: ${author}
 * @Date: ${dateTime?string["yyyy-MM-dd HH:mm:ss"]}
 * @Description: ${description} mvc api TODO
 */
@Api(tags = "${mvcDesc}-管理")
@ResponseBody
@Controller
@RequestMapping("${requestMapping}")
public class ${tableClass.shortClassName}Controller extends AbstractController<${tableClass.shortClassName}Po, ${tableClass.shortClassName}Dto, ${tableClass.shortClassName}Vo> {

    @Autowired
    private ${tableClass.shortClassName}Service ${tableClass.variableName}Service;

    /**
     * 根据ID获取信息
     *
     * @param id
     *
     * @return
     */
    @ApiOperation("根据ID获取${mvcDesc}")
    @ApiImplicitParam(name = "id", value = "${mvcDesc}ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "${mvcDesc}-管理", content = "根据ID获取${mvcDesc}")
    @RequiresPermissions("${permission}:getById")
    @GetMapping("/getById/{id}")
    public ResponseEntity<${tableClass.shortClassName}Dto> getById(@PathVariable("id") String id) {
        return super.getById(id);
    }

    /**
     * 列表
     *
     * @return
     */
    @ApiOperation("获取${mvcDesc}列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
        @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query")
    })
    @OperationLog(operation = "${mvcDesc}-管理", content = "获取${mvcDesc}列表")
    @RequiresPermissions("${permission}:list")
    @GetMapping("/list")
    public ResponseEntity<Page<${tableClass.shortClassName}Dto>> list(String keyword) {
        ${tableClass.shortClassName}Vo entity = new ${tableClass.shortClassName}Vo();
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
    @Resubmit
    @ApiOperation(value = "添加${mvcDesc}", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "${mvcDesc}-管理", content = "添加${mvcDesc}")
    @RequiresPermissions("${permission}:add")
    @PostMapping("add")
    public ResponseEntity add(@RequestBody ${tableClass.shortClassName}Dto entity) {
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
    @ApiOperation(value = "更新${mvcDesc}", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "${mvcDesc}-管理", content = "添加${mvcDesc}")
    @RequiresPermissions("${permission}:update")
    @PutMapping("update")
    public ResponseEntity update(@RequestBody ${tableClass.shortClassName}Dto entity) {
        return super.update(entity);
    }

    /**
     * 软删除
     *
     * @param id
     *
     * @return
     */
    @Resubmit(ParamType.url)
    @ApiOperation("根据ID删除${mvcDesc}")
    @ApiImplicitParam(name = "id", value = "${mvcDesc}ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "${mvcDesc}-管理", content = "根据ID删除${mvcDesc}")
    @RequiresPermissions("${permission}:delFlag")
    @DeleteMapping("/delFlag/{id}")
    public ResponseEntity deleteFlag(@PathVariable("id") String id) {
        return super.deleteFlag(id);
    }

}