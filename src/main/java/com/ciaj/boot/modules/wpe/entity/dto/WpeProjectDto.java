package com.ciaj.boot.modules.wpe.entity.dto;

import com.ciaj.boot.modules.wpe.entity.po.WpeProjectPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Ciaj.
 * @Date: 2025-07-27 09:31:59
 * @Description: www.ciaj.com gen DTO
 */
@ApiModel(value = "WpeProject")
public class WpeProjectDto extends WpeProjectPo {

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("主键")
    public java.lang.String getId() {
        return super.getId();
    }

    @Override
    @javax.validation.constraints.NotBlank(message = "工程项目名称不能为空")
    @ApiModelProperty("工程项目名称")
    public java.lang.String getProjectName() {
        return super.getProjectName();
    }

    @Override
    @javax.validation.constraints.NotBlank(message = "地址不能为空")
    @ApiModelProperty("地址")
    public java.lang.String getAddr() {
        return super.getAddr();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("备注")
    public java.lang.String getRemark() {
        return super.getRemark();
    }

    @Override
    @javax.validation.constraints.NotBlank(message = "项目户型不能为空")
    @ApiModelProperty("项目户型")
    public java.lang.String getHouseType() {
        return super.getHouseType();
    }

    @Override
    @javax.validation.constraints.NotBlank(message = "装修类型不能为空")
    @ApiModelProperty("装修类型")
    public java.lang.String getDecorationType() {
        return super.getDecorationType();
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
