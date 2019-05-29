package com.ciaj.boot.modules.oss.cloud;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.ciaj.comm.exception.BsRException;
import com.ciaj.comm.utils.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Author: Ciaj.
 * @Date: 2019/2/20 15:25
 * @Description: 阿里云存储
 */
public class AliyunCloudStorageService extends CloudStorageService {
	private OSSClient client;

	public AliyunCloudStorageService(CloudStorageConfig config) {
		this.config = config;
		//初始化
		init();
	}

	private void init() {
		client = new OSSClient(config.getAliyunEndPoint(), config.getAliyunAccessKeyId(),
				config.getAliyunAccessKeySecret());
	}

	@Override
	public String upload(byte[] data, String path) {
		return upload(new ByteArrayInputStream(data), path);
	}

	@Override
	public String uploadSuffix(byte[] data, String prefix, String suffix) {
		return upload(data, getPath(config.getAliyunPrefix() + "/" + prefix, suffix));
	}

	@Override
	public String upload(InputStream inputStream, String path) {
		try {
			inputStream = isCompress(config.isFileCompress(), inputStream, FileUtils.getExtention(path));
			client.putObject(config.getAliyunBucketName(), path, inputStream);
		} catch (Exception e) {
			throw new BsRException("上传文件失败，请检查配置信息", e);
		}

		return config.getAliyunDomain() + "/" + path;
	}

	@Override
	public String uploadSuffix(InputStream inputStream, String prefix, String suffix) {
		return upload(inputStream, getPath(config.getAliyunPrefix() + "/" + prefix, suffix));
	}

	@Override
	public byte[] download(String objectName) {
		try {
			ObjectMetadata object = client.getObject(new GetObjectRequest(config.getAliyunBucketName(), objectName), new File(UUID.randomUUID().toString()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.shutdown();
		}
		return null;
	}

	@Override
	public int delete(String objectName) {
		return 0;
	}


}
