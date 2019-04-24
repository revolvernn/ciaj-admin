package ${package};

import com.ciaj.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ${mapperPackage}.${tableClass.shortClassName}Mapper;
import ${poPackage}.${tableClass.shortClassName}Po;
import ${servicePackage}.${tableClass.shortClassName}Service;
import ${voPo}.${tableClass.shortClassName}Vo;
import ${dtoPo}.${tableClass.shortClassName}Dto;

<#assign dateTime = .now>
/**
 * @Author: ${author}
 * @Date: ${dateTime?string["yyyy-MM-dd HH:mm:ss"]}
 * @Description: ${description} service  实现
 */
@Service
public class ${tableClass.shortClassName}ServiceImpl extends AbstractService<${tableClass.shortClassName}Po, ${tableClass.shortClassName}Dto, ${tableClass.shortClassName}Vo> implements ${tableClass.shortClassName}Service {

    @Autowired
    private ${tableClass.shortClassName}Mapper ${tableClass.variableName}Mapper;
}