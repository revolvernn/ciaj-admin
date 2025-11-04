package com.ciaj.boot.modules.wpe.web;

import com.ciaj.base.AbstractController;
import com.ciaj.boot.modules.wpe.entity.dto.WpeElectricianRecordDto;
import com.ciaj.boot.modules.wpe.entity.po.WpeElectricianRecordPo;
import com.ciaj.boot.modules.wpe.entity.vo.WpeElectricianRecordVo;
import com.ciaj.boot.modules.wpe.service.WpeElectricianRecordService;
import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.annotation.OperationLog;
import com.ciaj.comm.annotation.Resubmit;
import com.ciaj.comm.constant.DefaultConstant;
import com.ciaj.comm.constant.ParamTypeEnum;
import com.ciaj.comm.utils.AssertUtil;
import com.ciaj.comm.utils.ExcelUtil;
import com.ciaj.comm.utils.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     * @return
     */
    @Override
    @ApiOperation("根据ID获取水电工程记录")
    @ApiImplicitParam(name = "id", value = "水电工程记录ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "水电工程记录-管理", content = "根据ID获取水电工程记录")
    @RequiresPermissions("wpe:electrician:record:getById")
    @GetMapping("getById/{id}")
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
            @ApiImplicitParam(name = "status", value = "工作状态", paramType = "query"),
            @ApiImplicitParam(name = "workType", value = "工作方式", paramType = "query")
    })
    @OperationLog(operation = "水电工程记录-管理", content = "获取水电工程记录列表")
    @RequiresPermissions("wpe:electrician:record:list")
    @GetMapping("list")
    public ResponseEntity<Page<WpeElectricianRecordDto>> list(String keyword, String userId, String projectId, String status, String workType) {
        WpeElectricianRecordVo entity = new WpeElectricianRecordVo();
        entity.setKeyword(keyword);
        entity.setStatus(status);
        entity.setUserId(userId);
        entity.setProjectId(projectId);
        entity.setWorkType(workType);
        return super.listDTOPage(entity);
    }

    /**
     * 列表导出
     *
     * @return
     */
    @ApiOperation("水电工程记录列表导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
            @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
            @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "query"),
            @ApiImplicitParam(name = "projectId", value = "项目ID", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "工作状态", paramType = "query"),
            @ApiImplicitParam(name = "workType", value = "工作方式", paramType = "query")
    })
    @OperationLog(operation = "水电工程记录-管理", content = "水电工程记录列表导出")
    @RequiresPermissions("wpe:electrician:record:list:export")
    @GetMapping("list/export")
    public void listExport(String keyword, String userId, String projectId, String status, String workType, HttpServletResponse response, HttpServletRequest request) {
        WpeElectricianRecordVo entity = new WpeElectricianRecordVo();
        entity.setKeyword(keyword);
        entity.setStatus(status);
        entity.setUserId(userId);
        entity.setProjectId(projectId);
        entity.setWorkType(workType);
        Page<WpeElectricianRecordDto> page = wpeElectricianRecordService.selectDTOPage(entity);
        List<WpeElectricianRecordDto> data = page.getList();
        //
        new ExcelUtil().build("水电工程项目导出",
                new String[]{
                        "id", "workday", "workStart", "workEnd", "remark", "status", "workType", "labourCost", "createTime", "updateTime"
                },
                new String[]{
                        "主键", "工作日", "工作开始时间", "工作日结束时间", "备注", "工作状态", "工作方式", "工价", "创建时间", "更新时间"
                }, data).exportExcel(request, response);
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
            @ApiImplicitParam(name = "period", value = "统计周期", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "统计类型：1/按月统计用户项目数据，2/按月统计用户数据，3/按年统计用户项目数据，4/按年统计用户数据", paramType = "query"),
    })
    @OperationLog(operation = "水电工程记录-管理", content = "获取水电工程记录统计列表")
    @RequiresPermissions("wpe:electrician:record:statistics")
    @GetMapping("statistics")
    public ResponseEntity<Page<Map<String, Object>>> statistics(String keyword, String userId, String projectId, String period, String type) {
        WpeElectricianRecordVo entity = new WpeElectricianRecordVo();
        entity.setKeyword(keyword);
        entity.setUserId(userId);
        entity.setProjectId(projectId);
        entity.setPeriod(period);
        entity.setType(type);
        entity.setDelFlag(DefaultConstant.FLAG_N);
        return new ResponseEntity<>("查询成功").put(wpeElectricianRecordService.statisticsPage(entity));
    }

    /**
     * 导出记录
     *
     * @return
     */
    @ApiOperation("水电工程记录统计列表导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderBy", value = "排序：xxx-desc,xxx-asc,xxx ", paramType = "query"),
            @ApiImplicitParam(name = "orderByEnabled", value = "是否开启排序:true/false 默认-false", dataType = "Boolean", paramType = "query"),
            @ApiImplicitParam(name = "pageEnabled", value = "是否开启分页:true/false 默认-false", dataType = "Boolean", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数：默认每页十条", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数：默认第一页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "query"),
            @ApiImplicitParam(name = "projectId", value = "项目ID", paramType = "query"),
            @ApiImplicitParam(name = "period", value = "统计周期", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "统计类型", paramType = "query"),
    })
    @RequiresPermissions("wpe:electrician:record:statistics:export")
    @GetMapping("statistics/export")
    public void export(String keyword, String userId, String projectId, String period, String type, HttpServletResponse response, HttpServletRequest request) {
        WpeElectricianRecordVo entity = new WpeElectricianRecordVo();
        entity.setKeyword(keyword);
        entity.setUserId(userId);
        entity.setProjectId(projectId);
        entity.setPeriod(period);
        entity.setDelFlag(DefaultConstant.FLAG_N);
        entity.setType(type);
        Page<Map<String, Object>> mapPage = wpeElectricianRecordService.statisticsPage(entity);
        List<Map<String, Object>> data = mapPage.getList();
        new ExcelUtil().build(StatType.getMsg(type),
                new String[]{"projectName", "period", "total", "totalLabourCost", "workdays", "workdayDs", "workdayDDs", "projectNum", "username"},
                new String[]{"项目", "月份", "天数", "工价", "日期", "日期明细1", "日期明细2", "项目数", "用户名"},
                data).exportExcel(request, response);
    }


    /**
     * 添加
     *
     * @param entity
     * @return
     */
    @Override
    @Resubmit
    @ApiOperation(value = "添加水电工程记录", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "水电工程记录-管理", content = "添加水电工程记录")
    @RequiresPermissions("wpe:electrician:record:add")
    @PostMapping("add")
    public ResponseEntity add(@RequestBody @Valid WpeElectricianRecordDto entity) {
        WpeElectricianRecordPo query = new WpeElectricianRecordPo();
        query.setWorkday(entity.getWorkday());
        query.setUserId(entity.getUserId());
        WpeElectricianRecordPo wpeElectricianRecordPo = wpeElectricianRecordService.selectOne(query);
        AssertUtil.isNull(wpeElectricianRecordPo, "添加失败，记录已经存在，请确认！");
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
    @ApiOperation(value = "更新水电工程记录", produces = "application/json;charset=UTF-8")
    @OperationLog(operation = "水电工程记录-管理", content = "添加水电工程记录")
    @RequiresPermissions("wpe:electrician:record:update")
    @PutMapping("update")
    public ResponseEntity update(@RequestBody  @Valid WpeElectricianRecordDto entity) {
        WpeElectricianRecordPo query = new WpeElectricianRecordPo();
        query.setWorkday(entity.getWorkday());
        query.setUserId(entity.getUserId());
        List<WpeElectricianRecordPo> list = wpeElectricianRecordService.selectAll(query);
        List<WpeElectricianRecordPo> filters = list.stream().filter(f -> !f.getId().equals(entity.getId())).collect(Collectors.toList());
        AssertUtil.isEmpty(filters, "更新失败，记录已经存在，请确认！");
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
    @ApiOperation("根据ID删除水电工程记录")
    @ApiImplicitParam(name = "id", value = "水电工程记录ID", required = true, dataType = "string", paramType = "path")
    @OperationLog(operation = "水电工程记录-管理", content = "根据ID删除水电工程记录")
    @RequiresPermissions("wpe:electrician:record:delFlag")
    @DeleteMapping("delFlag/{id}")
    public ResponseEntity deleteFlag(@PathVariable("id") String id) {
        return super.deleteFlag(id);
    }


    enum StatType {
        mouthStat("1","按月统计用户项目数据"),
        mouthStatProject("2","按月统计用户数据"),
        yearStat("3","按年统计用户项目数据"),
        yearStatProject("4","按年统计用户数据");

        StatType(String type, String msg){
            this.tyep = type;
            this.msg = msg;
        }
        String tyep;
        String msg;

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setTyep(String tyep) {
            this.tyep = tyep;
        }

        public String getTyep() {
            return tyep;
        }
        public static String getMsg(String type){
            StatType[] arr =  StatType.values();
            for (int i = 0; i < arr.length; i++) {
                if(arr[i].getTyep().equals(type)){
                    return arr[i].getMsg();
                }
            }
            return StatType.mouthStat.getMsg();
        }
    }

}
