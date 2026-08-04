package ${package};

import ${baseService};
import ${poPackage}.${tableClass.shortClassName}Po;
import ${voPo}.${tableClass.shortClassName}Vo;
import ${dtoPo}.${tableClass.shortClassName}Dto;

<#assign dateTime = .now>
/**
 * @Author: ${author}
 * @Date: ${dateTime?string["yyyy-MM-dd HH:mm:ss"]}
 * @Description: ${description} service  接口
 */
public interface ${tableClass.shortClassName}Service extends BaseService<${tableClass.shortClassName}Po, ${tableClass.shortClassName}Dto, ${tableClass.shortClassName}Vo> {
     /**
     * 根据ID查询
     * @param id
     * @return
     */
     public ${tableClass.shortClassName}Dto selectById(String id);
}
