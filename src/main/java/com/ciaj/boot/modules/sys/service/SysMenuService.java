package com.ciaj.boot.modules.sys.service;

import com.ciaj.base.BaseService;
import com.ciaj.boot.modules.sys.entity.dto.SysMenuDto;
import com.ciaj.boot.modules.sys.entity.po.SysMenuPo;
import com.ciaj.boot.modules.sys.entity.vo.SysMenuVo;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2018-12-06 14:02:06
 * @Description: www.ciaj.com service  接口
 */
public interface SysMenuService extends BaseService<SysMenuPo, SysMenuDto, SysMenuVo> {

    /**
     * 获取导航菜单
     *
     * @return
     */
    List<SysMenuPo> selectNav();
}
