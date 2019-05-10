package com.ciaj.boot.modules.sys.entity.dto;

import com.ciaj.boot.modules.sys.entity.po.SysAuthPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Ciaj.
 * @Date: 2019-05-09 09:17:54
 * @Description: www.ciaj.com gen DTO
 */
@ApiModel(value = "SysAuth")
public class SysAuthDto extends SysAuthPo {

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("主键")
    public java.lang.String getId() {
        return super.getId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("图片URL")
    public java.lang.String getUserId() {
        return super.getUserId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("密码")
    public java.lang.String getPassword() {
        return super.getPassword();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("密码盐")
    public java.lang.String getSalt() {
        return super.getSalt();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("token")
    public java.lang.String getToken() {
        return super.getToken();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("验证方式：pwd/token")
    public java.lang.String getType() {
        return super.getType();
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

}
