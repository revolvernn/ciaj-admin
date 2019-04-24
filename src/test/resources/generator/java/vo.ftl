package ${targetPackage};

import ${poPackage}.${tableClass.shortClassName}Po;

<#assign dateTime = .now>
/**
 * @Author: ${author}
 * @Date: ${dateTime?string["yyyy-MM-dd HH:mm:ss"]}
 * @Description: ${description} vo
 */
public class ${tableClass.shortClassName}Vo extends ${tableClass.shortClassName}Po {

}
