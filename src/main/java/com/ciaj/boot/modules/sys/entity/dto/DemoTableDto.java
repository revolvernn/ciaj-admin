package com.ciaj.boot.modules.sys.entity.dto;

import com.ciaj.boot.modules.sys.entity.po.DemoTablePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 17:54:57
 * @Description: www.ciaj.com DTO
 */
@ApiModel(value = "DemoTable")
public class DemoTableDto extends DemoTablePo {

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("主键 ")
    public java.lang.String getId() {
        return super.getId();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("图片URL ")
    public java.lang.String getUrl() {
        return super.getUrl();
    }

    //@javax.validation.constraints.NotBlank(message = "")
    @ApiModelProperty("来源 ")
    public java.lang.String getSource() {
        return super.getSource();
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

}
