package com.ciaj.boot.modules.oss.cloud;

import com.ciaj.comm.exception.BsRException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: Ciaj.
 * @Date: 2019/2/20 15:41
 * @Description: 七牛云存储
 */
public class QiniuCloudStorageService extends CloudStorageService {

	private UploadManager uploadManager;
	private String token;

	public QiniuCloudStorageService(CloudStorageConfig config) {
		this.config = config;
		//初始化
		init();
	}

	private void init() {
		uploadManager = new UploadManager(new Configuration(Zone.autoZone()));
		token = Auth.create(config.getQiniuAccessKey(), config.getQiniuSecretKey()).
				uploadToken(config.getQiniuBucketName());
	}

	@Override
	public String upload(byte[] data, String path) {
		try {
			data = IOUtils.toByteArray(isCompress(config.isFileCompress(), new ByteArrayInputStream(data), path));
			Response res = uploadManager.put(data, path, token);
			if (!res.isOK()) {
				throw new RuntimeException("上传七牛出错：" + res.toString());
			}
		} catch (Exception e) {
			throw new BsRException("上传文件失败，请核对七牛配置信息", e);
		}

		return config.getQiniuDomain() + "/" + path;
	}

	@Override
	public String uploadSuffix(byte[] data, String prefix, String suffix) {
		return upload(data, getPath(config.getQiniuPrefix() + "/" + prefix, suffix));
	}

	@Override
	public String upload(InputStream inputStream, String path) {
		try {
			byte[] data = IOUtils.toByteArray(inputStream);
			return this.upload(data, path);
		} catch (IOException e) {
			throw new BsRException("上传文件失败", e);
		}
	}

	@Override
	public String uploadSuffix(InputStream inputStream, String prefix, String suffix) {
		return upload(inputStream, getPath(config.getQiniuPrefix() + "/" + prefix, suffix));
	}

	@Override
	public byte[] download(String objectName) {

		return null;
	}

	@Override
	public int delete(String objectName) {
		return 0;
	}
}
