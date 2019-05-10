package com.ciaj.boot.modules.sys.entity.dto;

import com.ciaj.boot.modules.sys.entity.po.SysDictPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Ciaj.
 * @Date: 2019-05-09 12:55:06
 * @Description: www.ciaj.com gen DTO
 */
@ApiModel(value = "SysDict")
public class SysDictDto extends SysDictPo {

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("主键")
    public java.lang.String getId() {
        return super.getId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("字典名称")
    public java.lang.String getName() {
        return super.getName();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("字典编码")
    public java.lang.String getCode() {
        return super.getCode();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("父级")
    public java.lang.String getParentId() {
        return super.getParentId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("父级IDS")
    public java.lang.String getParentIds() {
        return super.getParentIds();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("类型：0公共   1其他")
    public java.lang.String getType() {
        return super.getType();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("级别")
    public java.lang.Integer getLevel() {
        return super.getLevel();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("排序")
    public java.lang.Integer getSequence() {
        return super.getSequence();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("是否可用Y/N")
    public java.lang.String getEnabled() {
        return super.getEnabled();
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
