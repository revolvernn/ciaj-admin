package com.ciaj.boot.modules.oss.cloud;

import com.ciaj.comm.exception.BsRException;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: Ciaj.
 * @Date: 2019/2/20 15:39
 * @Description: 腾讯云存储
 */
public class QcloudCloudStorageService extends CloudStorageService {
	private COSClient client;

	public QcloudCloudStorageService(CloudStorageConfig config) {
		this.config = config;
		//初始化
		init();
	}

	private void init() {
		Credentials credentials = new Credentials(config.getQcloudAppId(), config.getQcloudSecretId(),
				config.getQcloudSecretKey());

		//初始化客户端配置
		ClientConfig clientConfig = new ClientConfig();
		//设置bucket所在的区域，华南：gz 华北：tj 华东：sh
		clientConfig.setRegion(config.getQcloudRegion());

		client = new COSClient(clientConfig, credentials);
	}

	@Override
	public String upload(byte[] data, String path) {
		//腾讯云必需要以"/"开头
		if (!path.startsWith("/")) {
			path = "/" + path;
		}

		try {
			data = IOUtils.toByteArray(isCompress(config.isFileCompress(), new ByteArrayInputStream(data), path));
		} catch (IOException e) {
			throw new BsRException("文件上传失败");
		}
		//上传到腾讯云
		UploadFileRequest request = new UploadFileRequest(config.getQcloudBucketName(), path, data);
		String response = client.uploadFile(request);

		JSONObject jsonObject = JSONObject.fromObject(response);
		if (jsonObject.getInt("code") != 0) {
			throw new BsRException("文件上传失败，" + jsonObject.getString("message"));
		}

		return config.getQcloudDomain() + path;
	}

	@Override
	public String uploadSuffix(byte[] data, String prefix, String suffix) {
		return upload(data, getPath(config.getQcloudPrefix() + "/" + prefix, suffix));
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
		return upload(inputStream, getPath(config.getQcloudPrefix() + "/" + prefix, suffix));
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
