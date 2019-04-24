package com.ciaj.boot.modules.sys.service;

import com.ciaj.base.BaseService;
import com.ciaj.boot.modules.sys.entity.dto.SysDeptDto;
import com.ciaj.boot.modules.sys.entity.po.SysDeptPo;
import com.ciaj.boot.modules.sys.entity.vo.SysDeptVo;

/**
 * @Author: Ciaj.
 * @Date: 2019-01-14 14:48:23
 * @Description: www.ciaj.com service  接口
 */
public interface SysDeptService extends BaseService<SysDeptPo, SysDeptDto, SysDeptVo> {

    public SysDeptDto selectById(String id);
}
