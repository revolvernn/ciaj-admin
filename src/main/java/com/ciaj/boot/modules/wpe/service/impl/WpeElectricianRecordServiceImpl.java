package com.ciaj.boot.modules.wpe.service.impl;

import com.ciaj.base.AbstractService;
import com.ciaj.boot.modules.sys.entity.dto.SysUserDto;
import com.ciaj.boot.modules.sys.service.SysUserService;
import com.ciaj.boot.modules.wpe.entity.dto.WpeElectricianRecordDto;
import com.ciaj.boot.modules.wpe.entity.dto.WpeProjectDto;
import com.ciaj.boot.modules.wpe.entity.po.WpeElectricianRecordPo;
import com.ciaj.boot.modules.wpe.entity.po.WpeProjectPo;
import com.ciaj.boot.modules.wpe.entity.vo.WpeElectricianRecordVo;
import com.ciaj.boot.modules.wpe.mapper.WpeElectricianRecordMapper;
import com.ciaj.boot.modules.wpe.service.WpeElectricianRecordService;
import com.ciaj.boot.modules.wpe.service.WpeProjectService;
import com.ciaj.comm.utils.AssertUtil;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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


    @Autowired
    private WpeProjectService wpeProjectService;



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

        AssertUtil.notEmpty(wpeElectricianRecordDtos, "根据ID未查询到数据: " + id);
        WpeElectricianRecordDto wpeElectricianRecordDto = wpeElectricianRecordDtos.get(0);
        //
        SysUserDto sysUserDto = sysUserService.selectById(wpeElectricianRecordDto.getUserId());
        //
        wpeElectricianRecordDto.setUser(sysUserDto);

        //
        WpeProjectPo wpeProjectPo = wpeProjectService.selectByPrimaryKey(wpeElectricianRecordDto.getProjectId());
        WpeProjectDto wpeProjectDto = new WpeProjectDto();
        wpeProjectDto.setId(wpeProjectPo.getId());
        wpeProjectDto.setProjectName(wpeProjectPo.getProjectName());
        wpeProjectDto.setAddr(wpeProjectPo.getAddr());
        wpeProjectDto.setHouseType(wpeProjectPo.getHouseType());
        wpeProjectDto.setDecorationType(wpeProjectPo.getDecorationType());
        //
        wpeElectricianRecordDto.setProject(wpeProjectDto);

        return wpeElectricianRecordDto;
    }

    @Override
    public Page<Map<String, Object>> statisticsPage(WpeElectricianRecordVo entity) {
        com.github.pagehelper.Page p = PageUtils.startPageAndOrderBy();
        List<Map<String, Object>> maps = wpeElectricianRecordMapper.selectListStatisticsMultiTable(entity);
        return super.wrapPageMap(p, maps);
    }
}
