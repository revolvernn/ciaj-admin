package com.ciaj.boot.modules.my.service;

import com.ciaj.base.BaseService;
import com.ciaj.boot.modules.my.entity.po.MyFamilyDebitPo;
import com.ciaj.boot.modules.my.entity.vo.MyFamilyDebitVo;
import com.ciaj.boot.modules.my.entity.dto.MyFamilyDebitDto;

/**
 * @Author: Ciaj.
 * @Date: 2026-08-04 19:44:41
 * @Description: www.ciaj.com gen service  接口
 */
public interface MyFamilyDebitService extends BaseService<MyFamilyDebitPo, MyFamilyDebitDto, MyFamilyDebitVo> {
     /**
     * 根据ID查询
     * @param id
     * @return
     */
     public MyFamilyDebitDto selectById(String id);
}
