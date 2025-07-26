package com.ciaj.boot.modules.wpe.service;

import com.ciaj.base.BaseService;
import com.ciaj.boot.modules.wpe.entity.po.WpeElectricianRecordPo;
import com.ciaj.boot.modules.wpe.entity.vo.WpeElectricianRecordVo;
import com.ciaj.boot.modules.wpe.entity.dto.WpeElectricianRecordDto;
import com.ciaj.comm.utils.Page;

/**
 * @Author: Ciaj.
 * @Date: 2025-07-25 22:28:35
 * @Description: www.ciaj.com gen service  接口
 */
public interface WpeElectricianRecordService extends BaseService<WpeElectricianRecordPo, WpeElectricianRecordDto, WpeElectricianRecordVo> {


    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public WpeElectricianRecordDto selectById(String id);

    @Override
    Page<WpeElectricianRecordDto> selectDTOPage(WpeElectricianRecordPo entity);
}
