package com.ciaj.boot.modules.sys.service;

import com.ciaj.base.BaseService;
import com.ciaj.boot.modules.sys.entity.dto.SysConfigDto;
import com.ciaj.boot.modules.sys.entity.po.SysConfigPo;
import com.ciaj.boot.modules.sys.entity.vo.SysConfigVo;

/**
 * @Author: Ciaj.
 * @Date: 2019-02-20 14:55:21
 * @Description: www.ciaj.com service  接口
 */
public interface SysConfigService extends BaseService<SysConfigPo, SysConfigDto, SysConfigVo> {
    /**
     * 根据key，获取value的Object对象
     * @param key    key
     * @param clazz  Object对象
     */
    public <T> T getConfigObject(String key, Class<T> clazz);
}
