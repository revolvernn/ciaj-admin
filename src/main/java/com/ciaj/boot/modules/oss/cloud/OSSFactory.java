package com.ciaj.boot.modules.oss.cloud;

import com.ciaj.boot.component.config.SpringContextUtils;
import com.ciaj.boot.modules.sys.service.SysConfigService;
import com.ciaj.comm.constant.DefaultConfigConstant;

/**
 * @Author: Ciaj.
 * @Date: 2019/2/20 15:43
 * @Description:
 */
public final class OSSFactory {
    private static CloudStorageConfig config;

    static {
        SysConfigService sysConfigService = SpringContextUtils.getBean(SysConfigService.class);
        OSSFactory.config = sysConfigService.getConfigObject(DefaultConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);
    }

    public static CloudStorageService build(){
        //获取云存储配置信息

        if(config.getType().equals(DefaultConfigConstant.OSSCloud.QINIUCLOUD.getValue())){
            return new QiniuCloudStorageService(config);
        }else if(config.getType().equals(DefaultConfigConstant.OSSCloud.ALIYUN.getValue())){
            return new AliyunCloudStorageService(config);
        }else if(config.getType().equals(DefaultConfigConstant.OSSCloud.QCLOUD.getValue())){
            return new QcloudCloudStorageService(config);
        }

        return null;
    }

    public static String getType(){
        //获取云存储配置信息
        return DefaultConfigConstant.OSSCloud.getEnum(config.getType()).getName();
    }

}
