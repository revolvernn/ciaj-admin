package com.ciaj.boot.modules.my.entity.dto;

import com.ciaj.boot.modules.my.entity.po.MyFamilyPo;
import com.ciaj.boot.modules.sys.entity.dto.SysAreaDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Ciaj.
 * @Date: 2026-01-14 16:21:01
 * @Description: www.ciaj.com gen DTO
 */
@ApiModel(value = "MyFamily")
public class MyFamilyDto extends MyFamilyPo {

    private SysAreaDto sysArea;

    public SysAreaDto getSysArea() {
        return sysArea;
    }

    public void setSysArea(SysAreaDto sysArea) {
        this.sysArea = sysArea;
    }


    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("主键")
    public java.lang.String getId() {
        return super.getId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("家庭名称")
    public java.lang.String getName() {
        return super.getName();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("家庭编码")
    public java.lang.String getCode() {
        return super.getCode();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("是否可用Y/N")
    public java.lang.String getEnabled() {
        return super.getEnabled();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("家庭区域")
    public java.lang.String getAreaId() {
        return super.getAreaId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("家庭详细地址")
    public java.lang.String getAddr() {
        return super.getAddr();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("家庭描述")
    public java.lang.String getDescription() {
        return super.getDescription();
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
