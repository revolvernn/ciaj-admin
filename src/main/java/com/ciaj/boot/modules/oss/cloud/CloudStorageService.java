package com.ciaj.boot.modules.oss.cloud;

import com.ciaj.comm.utils.CalendarUtils;
import com.ciaj.comm.utils.ImageUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: Ciaj.
 * @Date: 2019/2/20 15:27
 * @Description: 云存储(支持七牛 、 阿里云 、 腾讯云)
 */
@Log4j2
public abstract class CloudStorageService {
	/**
	 * 云存储配置信息
	 */
	CloudStorageConfig config;

	/**
	 * 文件路径
	 *
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return 返回上传路径
	 */
	public String getPath(String prefix, String suffix) {
		//生成uuid
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		//文件路径
		String path = CalendarUtils.format(new Date(), "yyyyMMdd") + "/" + uuid;

		if (StringUtils.isNotBlank(prefix)) {
			path = prefix + "/" + path;
		}

		return path + suffix;
	}

	/**
	 * 是否压缩图片
	 *
	 * @param isCompress
	 * @param in
	 * @param originalFileExtention
	 * @return
	 */
	public InputStream isCompress(Boolean isCompress, InputStream in, String originalFileExtention) {
		// 启用文件压缩
		try {
			if (isCompress && ImageUtils.containsAny(originalFileExtention, ImageUtils.IMAGE_TYPE_JPEG, ImageUtils.IMAGE_TYPE_BMP, ImageUtils.IMAGE_TYPE_PNG, ImageUtils.IMAGE_TYPE_JPG)) {
				//
				BufferedImage image = ImageIO.read(in);
				in = ImageUtils.compressImage(image, ImageUtils.IMAGE_TYPE_JPEG, config.getFloatQuality());
			}
		} catch (IOException e) {
			log.error("启用文件压缩错误：", e);
		}
		return in;
	}

	/**
	 * 文件上传
	 *
	 * @param data 文件字节数组
	 * @param path 文件路径，包含文件名
	 * @return 返回http地址
	 */
	public abstract String upload(byte[] data, String path);

	/**
	 * 文件上传
	 *
	 * @param data       文件字节数组
	 * @param prefixPath 前缀路径
	 * @param suffix     后缀
	 * @return 返回http地址
	 */
	public abstract String uploadSuffix(byte[] data, String prefixPath, String suffix);

	/**
	 * 文件上传
	 *
	 * @param inputStream 字节流
	 * @param path        文件路径，包含文件名
	 * @return 返回http地址
	 */
	public abstract String upload(InputStream inputStream, String path);

	/**
	 * 文件上传
	 *
	 * @param inputStream 字节流
	 * @param prefixPath  前缀
	 * @param suffix      后缀
	 * @return 返回http地址
	 */
	public abstract String uploadSuffix(InputStream inputStream, String prefixPath, String suffix);

	/**
	 * 文件上传
	 *
	 * @param objectName 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
	 */
	public abstract byte[] download(String objectName);


	/**
	 * 删除文件
	 *
	 * @param objectName 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
	 */
	public abstract int delete(String objectName);

}
