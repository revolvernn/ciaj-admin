package ${targetPackage};

import ${poPackage}.${tableClass.shortClassName}Po;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

<#assign dateTime = .now>
/**
 * @Author: ${author}
 * @Date: ${dateTime?string["yyyy-MM-dd HH:mm:ss"]}
 * @Description: ${description} DTO
 */
@ApiModel(value = "${tableClass.shortClassName}")
public class ${tableClass.shortClassName}Dto extends ${tableClass.shortClassName}Po {

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
