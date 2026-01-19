package com.ciaj.boot.modules.my.service;

import com.ciaj.base.BaseService;
import com.ciaj.boot.modules.my.entity.po.MyFamilyPo;
import com.ciaj.boot.modules.my.entity.vo.MyFamilyVo;
import com.ciaj.boot.modules.my.entity.dto.MyFamilyDto;

/**
 * @Author: Ciaj.
 * @Date: 2026-01-14 16:21:01
 * @Description: www.ciaj.com gen service  接口
 */
public interface MyFamilyService extends BaseService<MyFamilyPo, MyFamilyDto, MyFamilyVo> {

    public MyFamilyDto selectById(String id);

}
