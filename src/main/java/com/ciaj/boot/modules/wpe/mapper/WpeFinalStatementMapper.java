package com.ciaj.boot.modules.wpe.mapper;

import com.ciaj.boot.modules.sys.mapper.SysUserMapper;
import com.ciaj.boot.modules.wpe.entity.po.WpeFinalStatementPo;
import com.ciaj.base.Mapper;
import com.ciaj.boot.modules.wpe.entity.vo.WpeFinalStatementVo;
import com.ciaj.boot.modules.wpe.entity.dto.WpeFinalStatementDto;
import com.ciaj.comm.annotation.MultiTableJoins;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: Ciaj.
 * @Date: 2025-09-01 16:06:42
 * @Description: www.ciaj.com gen DAO
 */
public interface WpeFinalStatementMapper extends Mapper<WpeFinalStatementPo, WpeFinalStatementDto, WpeFinalStatementVo> {

    /**
     *
     * @param entity
     * @return
     */
    @MultiTableJoins(mappers = {SysUserMapper.class})
    List<WpeFinalStatementDto> selectDTOListMultiTable(WpeFinalStatementVo entity);

    /**
     *
     * @param entity
     * @return
     */
    @MultiTableJoins(mappers = {WpeElectricianRecordMapper.class})
    List<Map<String, BigDecimal>> selectStatListMultiTable(WpeFinalStatementVo entity);
}
