package com.ciaj.boot.modules.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ciaj.base.AbstractService;
import com.ciaj.boot.component.config.shiro.ShiroUser;
import com.ciaj.boot.modules.sys.entity.dto.SysMenuDto;
import com.ciaj.boot.modules.sys.entity.po.SysMenuPo;
import com.ciaj.boot.modules.sys.entity.vo.SysMenuVo;
import com.ciaj.boot.modules.sys.mapper.SysMenuMapper;
import com.ciaj.boot.modules.sys.service.SysMenuService;
import com.ciaj.comm.constant.DefaultConstant;
import com.ciaj.comm.utils.CollectionUtil;
import com.ciaj.comm.utils.CommUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Ciaj.
 * @Date: 2018-12-06 14:02:06
 * @Description: www.ciaj.com service  实现
 */
@Service
@DS("mydb")
public class SysMenuServiceImpl extends AbstractService<SysMenuPo, SysMenuDto, SysMenuVo> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenuPo> selectNav() {
        final ShiroUser loginUser = CommUtil.getLoginUser();
        SysMenuPo q = new SysMenuPo();
        q.setDelFlag(DefaultConstant.FLAG_N);
        if (loginUser.isSuperAdmin()) {
            return sysMenuMapper.select(q);
        } else {
            List<SysMenuPo> sysMenus = sysMenuMapper.selectNavMultiTable(loginUser.getId(), loginUser.getRole() == null ? null : loginUser.getRole().getId());
            if (CollectionUtil.isEmpty(sysMenus)) {
                return null;
            }
            Set<String> ids = new HashSet<>();
            for (SysMenuPo sysMenu : sysMenus) {
                ids.add(sysMenu.getId());
                String[] parentIds = sysMenu.getParentIds().split(",");
                ids.addAll(Arrays.asList(parentIds));
            }
            return sysMenuMapper.selectNavByIds(ids);
        }
    }
}
