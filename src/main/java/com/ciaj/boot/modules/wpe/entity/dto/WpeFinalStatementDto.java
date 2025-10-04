package com.ciaj.boot.modules.wpe.entity.dto;

import com.ciaj.boot.modules.sys.entity.dto.SysUserDto;
import com.ciaj.boot.modules.wpe.entity.po.WpeFinalStatementPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Ciaj.
 * @Date: 2025-09-01 16:06:42
 * @Description: www.ciaj.com gen DTO
 */
@ApiModel(value = "WpeFinalStatement")
public class WpeFinalStatementDto extends WpeFinalStatementPo {

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("主键")
    public java.lang.String getId() {
        return super.getId();
    }

    @Override
    @javax.validation.constraints.NotBlank(message = "用户ID不能为空")
    @ApiModelProperty("用户ID")
    public java.lang.String getUserId() {
        return super.getUserId();
    }

    @Override
    @javax.validation.constraints.NotNull(message = "结算日不能为空")
    @ApiModelProperty("结算日")
    public java.util.Date getDay() {
        return super.getDay();
    }

    @Override
    @javax.validation.constraints.NotNull(message = "款项金额不能为空")
    @ApiModelProperty("款项金额")
    public java.math.BigDecimal getMoney() {
        return super.getMoney();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("备注")
    public java.lang.String getRemark() {
        return super.getRemark();
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

    /**
     * 记录用户
     */
    private SysUserDto user;

    public void setUser(SysUserDto user) {
        this.user = user;
    }

    public SysUserDto getUser() {
        return user;
    }
}
