package com.ciaj.boot.modules.wpe.service;

import com.ciaj.base.BaseService;
import com.ciaj.boot.modules.wpe.entity.po.WpeFinalStatementPo;
import com.ciaj.boot.modules.wpe.entity.vo.WpeFinalStatementVo;
import com.ciaj.boot.modules.wpe.entity.dto.WpeFinalStatementDto;
import com.ciaj.comm.utils.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: Ciaj.
 * @Date: 2025-09-01 16:06:42
 * @Description: www.ciaj.com gen service  接口
 */
public interface WpeFinalStatementService extends BaseService<WpeFinalStatementPo, WpeFinalStatementDto, WpeFinalStatementVo> {


    WpeFinalStatementDto selectById(String id);

    @Override
    Page<WpeFinalStatementDto> selectDTOPage(WpeFinalStatementVo entity);

    /**
     * 统计
     * @param entity
     * @return
     */
    List<Map<String, BigDecimal>> stat(WpeFinalStatementVo entity);
}
