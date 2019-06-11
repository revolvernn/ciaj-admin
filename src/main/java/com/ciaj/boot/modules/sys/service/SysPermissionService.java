package com.ciaj.boot.modules.sys.service;

import com.ciaj.base.BaseService;
import com.ciaj.boot.modules.sys.entity.dto.SysPermissionDto;
import com.ciaj.boot.modules.sys.entity.po.SysPermissionPo;
import com.ciaj.boot.modules.sys.entity.vo.SysPermissionVo;
import com.ciaj.comm.utils.Page;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2018-12-27 14:43:55
 * @Description: www.ciaj.com service  接口
 */
public interface SysPermissionService extends BaseService<SysPermissionPo, SysPermissionDto, SysPermissionVo> {

    /**
     *
     * @param userId
     * @return
     */
    List<SysPermissionPo> selectPermissionsByUserId(String userId);
    /**
     *
     * @param roleId
     * @return
     */
    List<SysPermissionPo> selectPermissionsByRoleId(String roleId);


    Page<SysPermissionDto> selectDTOPage(SysPermissionVo entity);

    SysPermissionDto getById(String id);
}
