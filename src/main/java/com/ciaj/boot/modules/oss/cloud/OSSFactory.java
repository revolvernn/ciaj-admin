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

	/**
	 * 构建存储实例
	 *
	 * @return
	 */
	public static CloudStorageService build() {
		if (config.getType().equals(DefaultConfigConstant.OSSCloud.QINIUCLOUD.getValue())) {
			return new QiniuCloudStorageService(config);
		} else if (config.getType().equals(DefaultConfigConstant.OSSCloud.ALIYUN.getValue())) {
			return new AliyunCloudStorageService(config);
		} else if (config.getType().equals(DefaultConfigConstant.OSSCloud.QCLOUD.getValue())) {
			return new QcloudCloudStorageService(config);
		} else if (config.getType().equals(DefaultConfigConstant.OSSCloud.LOCAL.getValue())) {
			return new LocalStorageService(config);
		}

		return null;
	}

	/**
	 * 获取当前配置名称
	 *
	 * @return
	 */
	public static String getType() {
		//获取云存储配置信息
		return DefaultConfigConstant.OSSCloud.getEnum(config.getType()).getName();
	}

	/**
	 * 是否压缩
	 *
	 * @return
	 */
	public static Boolean isCompress() {
		return config.isFileCompress();
	}

}
