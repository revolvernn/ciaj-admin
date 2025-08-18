package com.ciaj.boot.modules.sys.entity.dto;

import com.ciaj.boot.modules.sys.entity.po.SysLogPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Ciaj.
 * @Date: 2019-05-09 12:57:43
 * @Description: www.ciaj.com gen DTO
 */
@ApiModel(value = "SysLog")
public class SysLogDto extends SysLogPo {

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("主键")
    public java.lang.String getId() {
        return super.getId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("日志类型（system,operation,...）")
    public java.lang.String getType() {
        return super.getType();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("用户名")
    public java.lang.String getUsername() {
        return super.getUsername();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("请求IP")
    public java.lang.String getIp() {
        return super.getIp();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("请求时长(毫秒)")
    public java.lang.Long getTime() {
        return super.getTime();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("用户操作")
    public java.lang.String getOperation() {
        return super.getOperation();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("请求方法")
    public java.lang.String getMethod() {
        return super.getMethod();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("请求URL")
    public java.lang.String getUrl() {
        return super.getUrl();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("备注")
    public java.lang.String getDescription() {
        return super.getDescription();
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

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("请求参数")
    public java.lang.String getParams() {
        return super.getParams();
    }

}
