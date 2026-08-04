package ${targetPackage};

import ${poPackage}.${tableClass.shortClassName}Po;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ciaj.boot.modules.sys.entity.dto.SysDictDto;
import com.ciaj.boot.modules.sys.entity.dto.SysUserDto;

<#assign dateTime = .now>
/**
 * @Author: ${author}
 * @Date: ${dateTime?string["yyyy-MM-dd HH:mm:ss"]}
 * @Description: ${description} DTO
 */
@ApiModel(value = "${tableClass.shortClassName}")
public class ${tableClass.shortClassName}Dto extends ${tableClass.shortClassName}Po {
    public SysUserDto getUser() {
        return user;
    }

    public void setUser(SysUserDto user) {
        this.user = user;
    }

    /**
    * 用户
    */
    @ApiModelProperty("用户")
    private SysUserDto user;

    public SysDictDto getDict() {
        return dict;
    }

    public void setDict(SysDictDto dict) {
        this.dict = dict;
    }

    /**
    * 字典
    */
    @ApiModelProperty("字典")
    private SysDictDto dict;

<#if tableClass.allFields??>
<#list tableClass.allFields as field>
    //@javax.validation.constraints.NotBlank(message = "")
    @Override
    @ApiModelProperty("<#if field.remarks??>${field.remarks}</#if>")
    public ${field.fullTypeName} get${field.fieldName?cap_first}() {
        return super.get${field.fieldName?cap_first}();
    }

</#list>
</#if>
}
