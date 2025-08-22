package com.ciaj.boot.modules.wpe.entity.dto;

import com.ciaj.boot.component.serializer.DecimalFormat;
import com.ciaj.boot.modules.sys.entity.dto.SysUserDto;
import com.ciaj.boot.modules.wpe.entity.po.WpeElectricianRecordPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @Author: Ciaj.
 * @Date: 2025-07-25 22:28:35
 * @Description: www.ciaj.com gen DTO
 */
@ApiModel(value = "WpeElectricianRecord")
public class WpeElectricianRecordDto extends WpeElectricianRecordPo {

    /**
     * 记录用户
     */
    private SysUserDto user;

    /**
     * 记录项目
     */
    private WpeProjectDto project;


    public void setUser(SysUserDto user) {
        this.user = user;
    }

    public SysUserDto getUser() {
        return user;
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("主键")
    public java.lang.String getId() {
        return super.getId();
    }

    @javax.validation.constraints.NotBlank(message = "用户ID不能为空")
    @ApiModelProperty("用户ID")
    public java.lang.String getUserId() {
        return super.getUserId();
    }

    @javax.validation.constraints.NotBlank(message = "工程项目ID不能为空")
    @ApiModelProperty("工程项目ID")
    public java.lang.String getProjectId() {
        return super.getProjectId();
    }

    @javax.validation.constraints.NotNull(message = "工作日不能为空")
    @ApiModelProperty("工作日")
    public java.util.Date getWorkday() {
        return super.getWorkday();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("工作开始时间")
    public java.util.Date getWorkStart() {
        return super.getWorkStart();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("工作日结束时间")
    public java.util.Date getWorkEnd() {
        return super.getWorkEnd();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("备注")
    public java.lang.String getRemark() {
        return super.getRemark();
    }

    @javax.validation.constraints.NotBlank(message = "工作状态不能为空")
    @ApiModelProperty("工作状态")
    public java.lang.String getStatus() {
        return super.getStatus();
    }

    @DecimalFormat
    @javax.validation.constraints.NotNull(message = "工价不能为空")
    @ApiModelProperty("工价")
    public BigDecimal getLabourCost() {
        return super.getLabourCost();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("创建人")
    public java.lang.String getCreateAt() {
        return super.getCreateAt();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("创建时间")
    public java.util.Date getCreateTime() {
        return super.getCreateTime();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("更新人")
    public java.lang.String getUpdateAt() {
        return super.getUpdateAt();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("更新时间")
    public java.util.Date getUpdateTime() {
        return super.getUpdateTime();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("删除标记")
    public java.lang.String getDelFlag() {
        return super.getDelFlag();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("版本号0为不可修改，1+可修改")
    public java.lang.Integer getVersion() {
        return super.getVersion();
    }

    public WpeProjectDto getProject() {
        return project;
    }

    public void setProject(WpeProjectDto project) {
        this.project = project;
    }
}
