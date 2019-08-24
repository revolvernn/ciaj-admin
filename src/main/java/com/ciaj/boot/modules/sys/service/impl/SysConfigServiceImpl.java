package com.ciaj.boot.modules.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ciaj.base.AbstractService;
import com.ciaj.boot.modules.sys.entity.dto.SysConfigDto;
import com.ciaj.boot.modules.sys.entity.po.SysConfigPo;
import com.ciaj.boot.modules.sys.entity.vo.SysConfigVo;
import com.ciaj.boot.modules.sys.mapper.SysConfigMapper;
import com.ciaj.boot.modules.sys.service.SysConfigService;
import com.ciaj.comm.constant.DefaultConstant;
import com.ciaj.comm.exception.BsRException;
import com.ciaj.comm.utils.StringUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Ciaj.
 * @Date: 2019-02-20 14:55:21
 * @Description: www.ciaj.com service  实现
 */
@Service
@DS("mydb")
public class SysConfigServiceImpl extends AbstractService<SysConfigPo, SysConfigDto, SysConfigVo> implements SysConfigService {


    @Autowired
    private SysConfigMapper sysConfigMapper;


    private String getValue(String key) {

        SysConfigPo entity = new SysConfigPo();
        entity.setConfigKey(key);
        entity.setDelFlag(DefaultConstant.FLAG_N);
        SysConfigPo config = sysConfigMapper.selectOne(entity);
        return config == null ? null : config.getConfigValue();
    }

    @Override
    public <T> T getConfigObject(String key, Class<T> clazz) {
        String value = getValue(key);
        if(StringUtil.isNotBlank(value)){
            return new Gson().fromJson(value, clazz);
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BsRException("获取参数失败");
        }
    }
}
