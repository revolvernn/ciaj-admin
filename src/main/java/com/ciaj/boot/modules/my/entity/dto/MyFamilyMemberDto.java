package com.ciaj.boot.modules.my.entity.dto;

import com.ciaj.boot.modules.my.entity.po.MyFamilyMemberPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Ciaj.
 * @Date: 2026-01-14 16:48:58
 * @Description: www.ciaj.com gen DTO
 */
@ApiModel(value = "MyFamilyMember")
public class MyFamilyMemberDto extends MyFamilyMemberPo {

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("主键")
    public java.lang.String getId() {
        return super.getId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("家庭ID")
    public java.lang.String getFamilyId() {
        return super.getFamilyId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("用户ID")
    public java.lang.String getUserId() {
        return super.getUserId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("成员类型")
    public java.lang.String getType() {
        return super.getType();
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
