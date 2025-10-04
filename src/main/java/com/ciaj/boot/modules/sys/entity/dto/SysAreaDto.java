package com.ciaj.boot.modules.sys.entity.dto;

import com.ciaj.boot.modules.sys.entity.po.SysAreaPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Ciaj.
 * @Date: 2019-05-09 09:15:20
 * @Description: www.ciaj.com gen DTO
 */
@ApiModel(value = "SysArea")
public class SysAreaDto extends SysAreaPo {

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("主键")
    public java.lang.String getId() {
        return super.getId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("城市名称")
    public java.lang.String getName() {
        return super.getName();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("城市编码")
    public java.lang.String getCode() {
        return super.getCode();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("父级")
    public java.lang.String getParentId() {
        return super.getParentId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("父节点集合")
    public java.lang.String getParentIds() {
        return super.getParentIds();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("类型province省   city市 business_district 区")
    public java.lang.String getType() {
        return super.getType();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("级别")
    public java.lang.Integer getLevel() {
        return super.getLevel();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("排序")
    public java.lang.Integer getSequence() {
        return super.getSequence();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("是否可用Y/N")
    public java.lang.String getEnabled() {
        return super.getEnabled();
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
