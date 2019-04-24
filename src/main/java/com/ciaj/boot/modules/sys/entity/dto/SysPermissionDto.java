package com.ciaj.boot.modules.sys.entity.dto;

import com.ciaj.boot.modules.sys.entity.po.SysPermissionPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 16:41:56
 * @Description: www.ciaj.com DTO
 */
@ApiModel(value = "SysPermission")
public class SysPermissionDto extends SysPermissionPo {

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("主键 ")
    public java.lang.String getId() {
        return super.getId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("权限名 ")
    public java.lang.String getName() {
        return super.getName();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("权限描述 ")
    public java.lang.String getDescription() {
        return super.getDescription();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("权限类型 ")
    public java.lang.String getType() {
        return super.getType();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("权限码 ")
    public java.lang.String getPermissionCode() {
        return super.getPermissionCode();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("资源路径 ")
    public java.lang.String getUrl() {
        return super.getUrl();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("父级id ")
    public java.lang.String getParentId() {
        return super.getParentId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("父级ids ")
    public java.lang.String getParentIds() {
        return super.getParentIds();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("是否可用（Y/N） ")
    public java.lang.String getAvailable() {
        return super.getAvailable();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("创建人 ")
    public java.lang.String getCreateAt() {
        return super.getCreateAt();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("创建时间 ")
    public java.util.Date getCreateTime() {
        return super.getCreateTime();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("更新人 ")
    public java.lang.String getUpdateAt() {
        return super.getUpdateAt();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("更新时间 ")
    public java.util.Date getUpdateTime() {
        return super.getUpdateTime();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("删除标记 ")
    public java.lang.String getDelFlag() {
        return super.getDelFlag();
    }

}
