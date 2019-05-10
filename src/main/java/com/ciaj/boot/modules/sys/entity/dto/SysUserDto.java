package com.ciaj.boot.modules.sys.entity.dto;

import com.ciaj.boot.modules.sys.entity.po.SysUserPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Ciaj.
 * @Date: 2019-05-08 15:23:02
 * @Description: www.ciaj.com gen DTO
 */
@ApiModel(value = "SysUser")
public class SysUserDto extends SysUserPo {

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("主键 ")
    public java.lang.String getId() {
        return super.getId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("用户账号、手机号、邮箱、其他 ")
    public java.lang.String getAccount() {
        return super.getAccount();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("用户名 ")
    public java.lang.String getUsername() {
        return super.getUsername();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("昵称 ")
    public java.lang.String getNickname() {
        return super.getNickname();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("性别,0，女,1，男,2，未知 ")
    public java.lang.String getSex() {
        return super.getSex();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("电话号码 ")
    public java.lang.String getMobile() {
        return super.getMobile();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("邮箱 ")
    public java.lang.String getEmail() {
        return super.getEmail();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("生日 ")
    public java.util.Date getBirthday() {
        return super.getBirthday();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("用户所属省 ")
    public java.lang.String getProvince() {
        return super.getProvince();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("用户所属城市 ")
    public java.lang.String getCity() {
        return super.getCity();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("用户所属区 ")
    public java.lang.String getDistrict() {
        return super.getDistrict();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("用户详细地址 ")
    public java.lang.String getAddr() {
        return super.getAddr();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("来源，pc/系统  app/移动端 other/其他 ... ")
    public java.lang.String getSource() {
        return super.getSource();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("用户类型、org_user、组织用户，other_user、其他用户 ")
    public java.lang.String getUserType() {
        return super.getUserType();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("用户状态，在职inservice，离职中leaveing，已离职leave，重新雇佣 ")
    public java.lang.String getUserStatus() {
        return super.getUserStatus();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("锁定状态 Y/N ")
    public java.lang.String getLocked() {
        return super.getLocked();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("头像地址 ")
    public java.lang.String getPicUrl() {
        return super.getPicUrl();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("所在部门ID ")
    public java.lang.String getDeptId() {
        return super.getDeptId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("所在部门名称 ")
    public java.lang.String getDeptName() {
        return super.getDeptName();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("所在部门集合ID ")
    public java.lang.String getDeptIds() {
        return super.getDeptIds();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("所在部门集合名称 ")
    public java.lang.String getDeptNames() {
        return super.getDeptNames();
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

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("版本号0为不可修改，1+可修改 ")
    public java.lang.Integer getVersion() {
        return super.getVersion();
    }

}
