package com.ciaj.boot.modules.task.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.task.entity.po.TaskSchedulerPo;
import com.ciaj.boot.modules.task.entity.vo.TaskSchedulerVo;
import com.ciaj.boot.modules.task.entity.dto.TaskSchedulerDto;
import com.ciaj.boot.modules.task.service.TaskSchedulerService;
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
 * @Date: 2026-06-02 21:57:07
 * @Description: www.ciaj.com gen mvc api
 */
@Api(tags = "任务调度-管理")
@ResponseBody
@Controller
@RequestMapping("task/scheduler")
public class TaskSchedulerController extends AbstractController<TaskSchedulerPo, TaskSchedulerDto, TaskSchedulerVo> {

    @Autowired
    private TaskSchedulerService taskSchedulerService;

    /**
     * 根据ID获取信息
     *
     * @param id
     * @return
     */
    @Override
    @ApiOperation("根据ID获取任务调度")
    @ApiImplicitParam(name = "id", value = "任务调度ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "任务调度-管理", content = "根据ID获取任务调度")
    @RequiresPermissions("task:scheduler:getById")
    @GetMapping("getById/{id}")
    public ResponseEntity<TaskSchedulerDto> getById(@PathVariable("id") String id) {
        return super.getById(id);
    }

    /**
     * 列表
     *
     * @return
     */
    @ApiOperation("获取任务调度列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
            @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
            @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", paramType = "query")
    })
    @OperationLog(operation = "任务调度-管理", content = "获取任务调度列表")
    @RequiresPermissions("task:scheduler:list")
    @GetMapping("list")
    public ResponseEntity<Page<TaskSchedulerDto>> list(String keyword, String type, String status) {
        TaskSchedulerVo entity = new TaskSchedulerVo();
        entity.setType(type);
        entity.setStatus(status);
        entity.setKeyword(keyword);
        return super.listDTOPage(entity);
    }

    /**
     * 列表导出
     *
     * @return
     */
    @ApiOperation("任务调度列表导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
            @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
            @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", paramType = "query")
    })
    @OperationLog(operation = "任务调度-管理", content = "任务调度列表导出")
    @RequiresPermissions("task:scheduler:list:export")
    @GetMapping("list/export")
    public void listExport(String keyword, String type, String status, HttpServletResponse response, HttpServletRequest request) {
        TaskSchedulerVo entity = new TaskSchedulerVo();
        entity.setKeyword(keyword);
        entity.setType(type);
        entity.setStatus(status);
        entity.setDelFlag("N");
        //
        Page<TaskSchedulerDto> page = taskSchedulerService.selectDTOPage(entity);
        List<TaskSchedulerDto> data = page.getList();
        //
        new ExcelUtil().build("任务调度导出",
                new String[]{
                        "id", "type", "name", "remark", "beanName", "cron", "delay", "status", "createAt", "createTime", "updateAt", "updateTime", "delFlag", "version"
                },
                new String[]{
                        "主键", "类型fixedRate、fixedDelay、cron", "名称", "描述", "bean名称", "cron表达式", "间隔毫秒", "0 ok 1 禁用", "创建人", "创建时间", "更新人", "更新时间", "删除标记", "版本号0为不可修改，1+可修改"
                }, data).exportExcel(request, response);
    }

    /**
     * 添加
     *
     * @param entity
     * @return
     */
    @Override
    @Resubmit
    @ApiOperation(value = "添加任务调度", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "任务调度-管理", content = "添加任务调度")
    @RequiresPermissions("task:scheduler:add")
    @PostMapping("add")
    public ResponseEntity add(@RequestBody TaskSchedulerDto entity) {
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
    @ApiOperation(value = "更新任务调度", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "任务调度-管理", content = "更新任务调度")
    @RequiresPermissions("task:scheduler:update")
    @PutMapping("update")
    public ResponseEntity update(@RequestBody TaskSchedulerDto entity) {
        return super.update(entity);
    }

    /**
     * 软删除
     *
     * @param id
     * @return
     */
    @Override
    @Resubmit(ParamTypeEnum.url)
    @ApiOperation("根据ID删除任务调度")
    @ApiImplicitParam(name = "id", value = "任务调度ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "任务调度-管理", content = "根据ID删除任务调度")
    @RequiresPermissions("task:scheduler:delFlag")
    @DeleteMapping("delFlag/{id}")
    public ResponseEntity deleteFlag(@PathVariable("id") String id) {
        return super.delete(id);
    }
}
