package com.ciaj.boot.modules.sys.entity.dto;

import com.ciaj.boot.modules.sys.entity.po.SysConfigPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Ciaj.
 * @Date: 2019-05-09 09:11:04
 * @Description: www.ciaj.com gen DTO
 */
@ApiModel(value = "SysConfig")
public class SysConfigDto extends SysConfigPo {

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("主键")
    public java.lang.String getId() {
        return super.getId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("配置KEY")
    public java.lang.String getConfigKey() {
        return super.getConfigKey();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("配置VALUE")
    public java.lang.String getConfigValue() {
        return super.getConfigValue();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("配置状态")
    public java.lang.String getStatus() {
        return super.getStatus();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("备注")
    public java.lang.String getRemark() {
        return super.getRemark();
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
