package com.ciaj.boot.modules.task.entity.dto;

import com.ciaj.boot.modules.task.entity.po.TaskSchedulerPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Ciaj.
 * @Date: 2026-06-02 21:57:07
 * @Description: www.ciaj.com gen DTO
 */
@ApiModel(value = "TaskScheduler")
public class TaskSchedulerDto extends TaskSchedulerPo {

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("主键")
    public java.lang.String getId() {
        return super.getId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("类型fixedRate、fixedDelay、cron")
    public java.lang.String getType() {
        return super.getType();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("名称")
    public java.lang.String getName() {
        return super.getName();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("描述")
    public java.lang.String getRemark() {
        return super.getRemark();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("bean名称")
    public java.lang.String getBeanName() {
        return super.getBeanName();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("cron表达式")
    public java.lang.String getCron() {
        return super.getCron();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("间隔毫秒")
    public java.lang.Long getDelay() {
        return super.getDelay();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("0 ok 1 禁用")
    public java.lang.String getStatus() {
        return super.getStatus();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("创建人")
    public java.lang.String getCreateAt() {
        return super.getCreateAt();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("创建时间")
    public java.util.Date getCreateTime() {
        return super.getCreateTime();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("更新人")
    public java.lang.String getUpdateAt() {
        return super.getUpdateAt();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("更新时间")
    public java.util.Date getUpdateTime() {
        return super.getUpdateTime();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("删除标记")
    public java.lang.String getDelFlag() {
        return super.getDelFlag();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("版本号0为不可修改，1+可修改")
    public java.lang.Integer getVersion() {
        return super.getVersion();
    }

}
