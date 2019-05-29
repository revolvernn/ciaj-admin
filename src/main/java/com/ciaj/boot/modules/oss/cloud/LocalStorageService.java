package com.ciaj.boot.modules.oss.cloud;

import com.ciaj.comm.utils.*;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.opc.internal.FileHelper;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: Ciaj.
 * @Date: 2019/2/20 15:41
 * @Description: 七牛云存储
 */
public class LocalStorageService extends CloudStorageService {

	public LocalStorageService(CloudStorageConfig config) {
		this.config = config;
	}


	/**
	 * 把相对路径拼接根路径以获取真实路径
	 *
	 * @param relativePath
	 * @return
	 */
	public String getRealPath(String rootPath, String relativePath) {
		return rootPath + wrapPath(relativePath);
	}

	/**
	 * 整理path
	 *
	 * @param path
	 * @return 如：/a/b
	 */
	public String wrapPath(String path) {

		if (StringUtils.isNotEmpty(path)) {
			// 不充许路径回退符
			if (StringUtils.contains(path, "..")) {
				throw new RuntimeException("path can not include ..");
			}
			path = path.replace("/", File.separator);
			if (!path.startsWith(File.separator)) {
				path = File.separator + path;
			}
			if (path.endsWith(File.separator)) {
				path = path.substring(0, path.length() - 1);
			}
			return path;
		}
		return "";
	}



	/**
	 * 保存输入流到硬盘
	 *
	 * @param in
	 * @param originalFilename
	 * @return
	 */
	public String saveToDisk(InputStream in, String originalFilename, String rootPath, String relativePath) throws IOException {
		// 新文件名
		String newFileName = UUID.randomUUID().toString();
		// 新文件名全名
		String newOriginalFilename = newFileName;
		String originalFileExtention = FileUtils.getExtention(originalFilename);
		if (StringUtils.isNotEmpty(originalFileExtention)) {
			newOriginalFilename += "." + originalFileExtention;
		}
		String realPath = getRealPath(rootPath, relativePath);
		if (!FileUtils.exists(realPath)) {
			FileUtils.createFolder(realPath);
		}
		// 组装成带文件名的全路径
		realPath = realPath + File.separator + newOriginalFilename;
		FileUtils.createFile(realPath, in);

		String resultPath = wrapPath(relativePath) + File.separator + newOriginalFilename;
		return resultPath;
	}

	/**
	 * 删除文件
	 *
	 * @param fileRelativePath
	 */
	public void deleteDiskFile(String rootPath, String fileRelativePath) {
		String realPath = getRealPath(rootPath, fileRelativePath);
		if (FileUtils.exists(realPath)) {
			FileUtils.deleteFile(realPath);
		}
	}

	public byte[] download(String path) {
		try {
			return StreamUtils.inputStreamToByteArray(new FileInputStream(new File(config.getLocalFilePath() + "/" + path)));
		} catch (IOException e) {
		}
		return null;
	}


	@Override
	public String upload(byte[] data, String path) {
		return upload(new ByteArrayInputStream(data),path);
	}

	public String uploadSuffix(byte[] data, String prefixPath, String suffix) {
		return upload(new ByteArrayInputStream(data), getPath(config.getLocalFilePrefix() + "/" + prefixPath, suffix));
	}

	public String upload(InputStream inputStream, String filepath) {
		String resultPath = null;
		try {
			String originalFileExtention = FileUtils.getExtention(filepath);

			inputStream = isCompress(config.isFileCompress(), inputStream, originalFileExtention);

			String filenameTemp = "";
			if (StringUtils.isNotEmpty(originalFileExtention)) {
				filenameTemp += "temp." + originalFileExtention;
			}
			String relativePath = "";
			if (StringUtils.isNotEmpty(filepath)) {
				relativePath = filepath.substring(0, filepath.lastIndexOf("/"));
			}
			resultPath = saveToDisk(inputStream, filenameTemp, config.getLocalFilePath(), relativePath);
		} catch (IOException e) {
		}
		if (StringUtils.isNotEmpty(resultPath)) {
			resultPath = RequestUtils.convertToSlash(resultPath);
		}
		//添加访问映射
		resultPath = config.getLocalFileMapping() + resultPath;
		return resultPath;
	}

	@Override
	public String uploadSuffix(InputStream inputStream, String prefixPath, String suffix) {
		return upload(inputStream, getPath(config.getLocalFilePath() + "/" + config.getLocalFilePrefix(), suffix));
	}



	public int delete(String objectName) {
		int r = 0;
		try {
			deleteDiskFile(config.getLocalFilePath() + "/" + config.getLocalFilePrefix(), objectName);
			r = 1;
		} catch (Exception e) {
			r = 0;
		}
		return r;
	}
}
