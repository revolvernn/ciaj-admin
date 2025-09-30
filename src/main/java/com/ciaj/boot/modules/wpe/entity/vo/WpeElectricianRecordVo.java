package com.ciaj.boot.modules.wpe.entity.vo;

import com.ciaj.boot.modules.wpe.entity.po.WpeElectricianRecordPo;

/**
 * @Author: Ciaj.
 * @Date: 2025-07-25 22:28:35
 * @Description: www.ciaj.com gen vo
 */
public class WpeElectricianRecordVo extends WpeElectricianRecordPo {

    /**
     * 统计周期
     */
    private String  period;

    /**
     * 统计类型：1/按月统计用户项目数据，2/按月统计用户数据，3/按年统计用户项目数据，4/按年统计用户数据
     */
    private String  type;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriod() {
        return period;
    }
}
