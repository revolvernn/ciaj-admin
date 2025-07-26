package com.ciaj.boot.modules.wpe.mapper;

import com.ciaj.boot.modules.sys.mapper.SysUserMapper;
import com.ciaj.boot.modules.wpe.entity.po.WpeElectricianRecordPo;
import com.ciaj.base.Mapper;
import com.ciaj.boot.modules.wpe.entity.vo.WpeElectricianRecordVo;
import com.ciaj.boot.modules.wpe.entity.dto.WpeElectricianRecordDto;
import com.ciaj.comm.annotation.MultiTableJoins;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2025-07-25 22:28:35
 * @Description: www.ciaj.com gen DAO
 */
public interface WpeElectricianRecordMapper extends Mapper<WpeElectricianRecordPo, WpeElectricianRecordDto, WpeElectricianRecordVo> {

    @MultiTableJoins(mappers = {SysUserMapper.class})
    List<WpeElectricianRecordDto> selectDTOListMultiTable(WpeElectricianRecordVo entity);
}
