package com.ciaj.boot.modules.wpe.service.impl;

import com.ciaj.base.AbstractService;
import com.ciaj.boot.modules.sys.entity.dto.SysUserDto;
import com.ciaj.boot.modules.sys.mapper.SysUserMapper;
import com.ciaj.boot.modules.sys.service.SysUserService;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciaj.boot.modules.wpe.mapper.WpeElectricianRecordMapper;
import com.ciaj.boot.modules.wpe.entity.po.WpeElectricianRecordPo;
import com.ciaj.boot.modules.wpe.service.WpeElectricianRecordService;
import com.ciaj.boot.modules.wpe.entity.vo.WpeElectricianRecordVo;
import com.ciaj.boot.modules.wpe.entity.dto.WpeElectricianRecordDto;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2025-07-25 22:28:35
 * @Description: www.ciaj.com gen service  实现
 */
@Service
public class WpeElectricianRecordServiceImpl extends AbstractService<WpeElectricianRecordPo, WpeElectricianRecordDto, WpeElectricianRecordVo> implements WpeElectricianRecordService {

    @Autowired
    private WpeElectricianRecordMapper wpeElectricianRecordMapper;

    @Autowired
    private SysUserService sysUserService;



    @Override
    public Page<WpeElectricianRecordDto> selectDTOPage(WpeElectricianRecordVo entity) {
        com.github.pagehelper.Page p = PageUtils.startPageAndOrderBy();
        List<WpeElectricianRecordDto> wpeElectricianRecordDtos = wpeElectricianRecordMapper.selectDTOListMultiTable(entity);
        return wrapPageDTO(p, wpeElectricianRecordDtos);
    }

    @Override
    public WpeElectricianRecordDto selectById(String id) {
        WpeElectricianRecordVo n = new WpeElectricianRecordVo();
        n.setId(id);
        List<WpeElectricianRecordDto> wpeElectricianRecordDtos = wpeElectricianRecordMapper.selectDTOList(n);
        WpeElectricianRecordDto wpeElectricianRecordDto = wpeElectricianRecordDtos.get(0);
        //
        SysUserDto sysUserDto = sysUserService.selectById(wpeElectricianRecordDto.getUserId());
        //
        wpeElectricianRecordDto.setUser(sysUserDto);
        return wpeElectricianRecordDto;
    }
}
