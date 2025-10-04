package com.ciaj.boot.modules.sys.entity.dto;

import com.ciaj.boot.modules.sys.entity.po.SysMenuPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Ciaj.
 * @Date: 2019-05-09 13:00:46
 * @Description: www.ciaj.com gen DTO
 */
@ApiModel(value = "SysMenu")
public class SysMenuDto extends SysMenuPo {

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("主键")
    public java.lang.String getId() {
        return super.getId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("name")
    public java.lang.String getName() {
        return super.getName();
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
    @ApiModelProperty("菜单url")
    public java.lang.String getUrl() {
        return super.getUrl();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("权限码")
    public java.lang.String getPermissionCode() {
        return super.getPermissionCode();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("类型：0目录   1菜单   2按钮")
    public java.lang.String getType() {
        return super.getType();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("图标")
    public java.lang.String getIcon() {
        return super.getIcon();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("排序")
    public java.lang.Integer getSequence() {
        return super.getSequence();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("是否启用：Y/N")
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
