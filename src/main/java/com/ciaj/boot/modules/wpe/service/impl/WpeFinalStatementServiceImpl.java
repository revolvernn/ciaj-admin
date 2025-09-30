package com.ciaj.boot.modules.wpe.service.impl;

import com.ciaj.base.AbstractService;
import com.ciaj.boot.modules.sys.entity.dto.SysUserDto;
import com.ciaj.boot.modules.sys.service.SysUserService;
import com.ciaj.boot.modules.wpe.entity.dto.WpeElectricianRecordDto;
import com.ciaj.comm.utils.AssertUtil;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciaj.boot.modules.wpe.mapper.WpeFinalStatementMapper;
import com.ciaj.boot.modules.wpe.entity.po.WpeFinalStatementPo;
import com.ciaj.boot.modules.wpe.service.WpeFinalStatementService;
import com.ciaj.boot.modules.wpe.entity.vo.WpeFinalStatementVo;
import com.ciaj.boot.modules.wpe.entity.dto.WpeFinalStatementDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: Ciaj.
 * @Date: 2025-09-01 16:06:42
 * @Description: www.ciaj.com gen service  实现
 */
@Service
public class WpeFinalStatementServiceImpl extends AbstractService<WpeFinalStatementPo, WpeFinalStatementDto, WpeFinalStatementVo> implements WpeFinalStatementService {

    @Autowired
    private WpeFinalStatementMapper wpeFinalStatementMapper;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public WpeFinalStatementDto selectById(String id) {
        WpeFinalStatementVo n = new WpeFinalStatementVo();
        n.setId(id);
        List<WpeFinalStatementDto> wpeFinalStatementDtos = wpeFinalStatementMapper.selectDTOList(n);
        AssertUtil.notEmpty(wpeFinalStatementDtos, "根据ID未查询到数据: " + id);

        WpeFinalStatementDto wpeFinalStatementDto = wpeFinalStatementDtos.get(0);
        SysUserDto sysUserDto = sysUserService.selectById(wpeFinalStatementDto.getUserId());
        wpeFinalStatementDto.setUser(sysUserDto);
        return wpeFinalStatementDto;
    }

    @Override
    public Page<WpeFinalStatementDto> selectDTOPage(WpeFinalStatementVo entity) {
        com.github.pagehelper.Page p = PageUtils.startPageAndOrderBy();
        List<WpeFinalStatementDto> wpeFinalStatementDtos = wpeFinalStatementMapper.selectDTOListMultiTable(entity);
        return wrapPageDTO(p, wpeFinalStatementDtos);
    }

    @Override
    public List<Map<String, BigDecimal>> stat(WpeFinalStatementVo entity) {
        return wpeFinalStatementMapper.selectStatListMultiTable(entity);
    }
}
