package com.ciaj.boot.modules.my.service;

import com.ciaj.base.BaseService;
import com.ciaj.boot.modules.my.entity.po.MyFamilyBillPo;
import com.ciaj.boot.modules.my.entity.vo.MyFamilyBillVo;
import com.ciaj.boot.modules.my.entity.dto.MyFamilyBillDto;

/**
 * @Author: Ciaj.
 * @Date: 2026-07-13 22:34:02
 * @Description: www.ciaj.com gen service  接口
 */
public interface MyFamilyBillService extends BaseService<MyFamilyBillPo, MyFamilyBillDto, MyFamilyBillVo> {
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public MyFamilyBillDto selectById(String id);
}
