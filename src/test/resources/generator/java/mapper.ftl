package ${package};

import ${poPackage}.${tableClass.shortClassName}Po;
import ${baseMapper};
import ${voQm}.${tableClass.shortClassName}Vo;
import ${dtoPo}.${tableClass.shortClassName}Dto;
import com.ciaj.comm.annotation.MultiTableJoins;
import com.ciaj.boot.modules.sys.mapper.SysUserMapper;
import java.util.List;

<#assign dateTime = .now>
/**
 * @Author: ${author}
 * @Date: ${dateTime?string["yyyy-MM-dd HH:mm:ss"]}
 * @Description: ${description} DAO
 */
public interface ${tableClass.shortClassName}${mapperSuffix} extends Mapper<${tableClass.shortClassName}Po, ${tableClass.shortClassName}Dto, ${tableClass.shortClassName}Vo> {
    /**
    * 多表列表查询
    * @param entity
    * @return
    */
    @MultiTableJoins(mappers = {SysUserMapper.class})
    List<${tableClass.shortClassName}Dto> selectDTOListMultiTable(${tableClass.shortClassName}Vo entity);
}
