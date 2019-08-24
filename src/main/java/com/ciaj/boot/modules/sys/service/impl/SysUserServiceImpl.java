package com.ciaj.boot.modules.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ciaj.base.AbstractService;
import com.ciaj.boot.modules.sys.entity.dto.SysDeptDto;
import com.ciaj.boot.modules.sys.entity.po.SysAreaPo;
import com.ciaj.boot.modules.sys.service.SysAreaService;
import com.ciaj.boot.modules.sys.service.SysDeptService;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.PageUtils;
import com.ciaj.comm.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciaj.boot.modules.sys.mapper.SysUserMapper;
import com.ciaj.boot.modules.sys.entity.po.SysUserPo;
import com.ciaj.boot.modules.sys.service.SysUserService;
import com.ciaj.boot.modules.sys.entity.vo.SysUserVo;
import com.ciaj.boot.modules.sys.entity.dto.SysUserDto;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 17:02:08
 * @Description: www.ciaj.com service  实现
 */
@Service
@DS("mydb")
public class SysUserServiceImpl extends AbstractService<SysUserPo, SysUserDto, SysUserVo> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysAreaService sysAreaService;

    @Autowired
    private SysDeptService sysDeptService;

    @Override
    public SysUserDto selectById(String userId) {
        SysUserVo vo = new SysUserVo();
        vo.setId(userId);
        List<SysUserDto> sysUserDtos = sysUserMapper.selectDTOList(vo);
        SysUserDto sysUserDto = sysUserDtos.get(0);
        if(StringUtil.isNotBlank(sysUserDto.getProvince())){
            SysAreaPo sysAreaPo = sysAreaService.selectByPrimaryKey(sysUserDto.getProvince());
            sysUserDto.setSysProvince(sysAreaService.poToDto(sysAreaPo));
        }
        if(StringUtil.isNotBlank(sysUserDto.getCity())){
            SysAreaPo sysAreaPo = sysAreaService.selectByPrimaryKey(sysUserDto.getCity());
            sysUserDto.setSysCity(sysAreaService.poToDto(sysAreaPo));
        }
        if(StringUtil.isNotBlank(sysUserDto.getDistrict())){
            SysAreaPo sysAreaPo = sysAreaService.selectByPrimaryKey(sysUserDto.getDistrict());
            sysUserDto.setSysDistrict(sysAreaService.poToDto(sysAreaPo));
        }

        if(StringUtil.isNotBlank(sysUserDto.getDeptId())){
            SysDeptDto sysDeptDto = sysDeptService.selectById(sysUserDto.getDeptId());
            sysUserDto.setDept(sysDeptDto);
        }

        return sysUserDto;
    }

    @Override
    public Page<SysUserDto> selectDTOPage(SysUserVo entity) {
        com.github.pagehelper.Page p = PageUtils.startPageAndOrderBy();
        List<SysUserDto> sysUserDtos = sysUserMapper.selectDTOListMultiTable(entity);
        return wrapPageDTO(p, sysUserDtos);
    }
}
