package ${package};

import com.ciaj.base.AbstractService;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.PageUtils;
import com.ciaj.comm.utils.Safes;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ${mapperPackage}.${tableClass.shortClassName}Mapper;
import ${poPackage}.${tableClass.shortClassName}Po;
import ${servicePackage}.${tableClass.shortClassName}Service;
import ${voPo}.${tableClass.shortClassName}Vo;
import ${dtoPo}.${tableClass.shortClassName}Dto;

import java.util.List;

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

    @Override
    public Page<${tableClass.shortClassName}Dto> selectDTOListMultiTablePage(${tableClass.shortClassName}Vo entity) {
        com.github.pagehelper.Page page = PageUtils.startPageAndOrderBy();
        List<${tableClass.shortClassName}Dto> ${tableClass.variableName}Dtos = ${tableClass.variableName}Mapper.selectDTOListMultiTable(entity);
        return wrapPageDTO(page, ${tableClass.variableName}Dtos);
    }

    @Override
    public ${tableClass.shortClassName}Dto selectById(String id) {

        ${tableClass.shortClassName}Vo queryVo = new  ${tableClass.shortClassName}Vo();
        queryVo.setId(id);
        List<${tableClass.shortClassName}Dto> ${tableClass.variableName}Dtos = ${tableClass.variableName}Mapper.selectDTOListMultiTable(queryVo);

        return Safes.of(${tableClass.variableName}Dtos).findFirst().orElse(null);
    }
}