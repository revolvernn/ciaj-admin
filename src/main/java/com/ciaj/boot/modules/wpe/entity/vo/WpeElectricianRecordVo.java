package com.ciaj.boot.modules.wpe.entity.vo;

import com.ciaj.boot.modules.wpe.entity.po.WpeElectricianRecordPo;

/**
 * @Author: Ciaj.
 * @Date: 2025-07-25 22:28:35
 * @Description: www.ciaj.com gen vo
 */
public class WpeElectricianRecordVo extends WpeElectricianRecordPo {


    public String getWorkMonth() {
        return workMonth;
    }

    public void setWorkMonth(String workMonth) {
        this.workMonth = workMonth;
    }

    /**
     * 年月
     */
    private String  workMonth;



}
