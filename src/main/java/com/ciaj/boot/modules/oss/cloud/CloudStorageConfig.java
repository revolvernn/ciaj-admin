package com.ciaj.boot.modules.oss.cloud;

import com.ciaj.comm.utils.RegexpUtil;
import com.ciaj.comm.validate.AliyunGroup;
import com.ciaj.comm.validate.LocalGroup;
import com.ciaj.comm.validate.QcloudGroup;
import com.ciaj.comm.validate.QiniuGroup;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Author: Ciaj.
 * @Date: 2019/2/20 15:21
 * @Description:
 */
public class CloudStorageConfig implements Serializable {
	private static final long serialVersionUID = 3492572823723502561L;

	//类型 QNY：七牛云  ALY：阿里云  TXY：腾讯云
	@NotBlank(message = "类型不能为空")
	private String type;


	//状态 是否启用 Y/N
	@NotBlank(message = "状态不能为空")
	private String status;
	/**
	 * 文件是否开启压缩：TRUE/FALSE
	 */
	@NotBlank(message = "文件是否开启压缩不能为空")
	private String fileCompress = "false";
	/**
	 * 文件压缩质量
	 */
	@NotBlank(message = "文件压缩质量不能为空")
	@Pattern(regexp = RegexpUtil.REG_INTEGER_OR_FLOAT, message = "文件压缩质量格式为正整数或正浮点数")
	private String fileQuality = "0.3";
	/**
	 * 本地文件前缀
	 */
	@NotBlank(message = "本地文件前缀不能为空", groups = LocalGroup.class)
	@Pattern(regexp = RegexpUtil.REG_W, groups = LocalGroup.class, message = "本地文件前缀格式不正确")
	private String localFilePrefix;
	/**
	 * 本地文件绝对路径
	 * reg (^/{0}(/?(\w)+)+(\w)$)|(^[a-zA-Z]{1}:(\/(\w)+)+(\w)$)
	 * /linux/path
	 * d:/windows/path
	 */
	@NotBlank(message = "本地文件绝对路径不能为空", groups = LocalGroup.class)
	@Pattern(regexp = RegexpUtil.REG_ABSOLUTE_PATH, groups = LocalGroup.class, message = "本地文件绝对路径格式不正确")
	private String localFilePath;
	/**
	 * 本地文件访问映射
	 */
	@NotBlank(message = "本地文件访问映射不能为空", groups = LocalGroup.class)
	@Pattern(regexp = RegexpUtil.REG_URL_MAPPING, groups = LocalGroup.class, message = "本地文件访问映射格式不正确")
	private String localFileMapping;


	//七牛绑定的域名
	@NotBlank(message = "七牛绑定的域名不能为空", groups = QiniuGroup.class)
	@URL(message = "七牛绑定的域名格式不正确", groups = QiniuGroup.class)
	private String qiniuDomain;
	//七牛路径前缀
	private String qiniuPrefix;
	//七牛ACCESS_KEY
	@NotBlank(message = "七牛AccessKey不能为空", groups = QiniuGroup.class)
	private String qiniuAccessKey;
	//七牛SECRET_KEY
	@NotBlank(message = "七牛SecretKey不能为空", groups = QiniuGroup.class)
	private String qiniuSecretKey;
	//七牛存储空间名
	@NotBlank(message = "七牛空间名不能为空", groups = QiniuGroup.class)
	private String qiniuBucketName;

	//阿里云绑定的域名
	@NotBlank(message = "阿里云绑定的域名不能为空", groups = AliyunGroup.class)
	@URL(message = "阿里云绑定的域名格式不正确", groups = AliyunGroup.class)
	private String aliyunDomain;
	//阿里云路径前缀
	private String aliyunPrefix;
	//阿里云EndPoint
	@NotBlank(message = "阿里云EndPoint不能为空", groups = AliyunGroup.class)
	private String aliyunEndPoint;
	//阿里云AccessKeyId
	@NotBlank(message = "阿里云AccessKeyId不能为空", groups = AliyunGroup.class)
	private String aliyunAccessKeyId;
	//阿里云AccessKeySecret
	@NotBlank(message = "阿里云AccessKeySecret不能为空", groups = AliyunGroup.class)
	private String aliyunAccessKeySecret;
	//阿里云BucketName
	@NotBlank(message = "阿里云BucketName不能为空", groups = AliyunGroup.class)
	private String aliyunBucketName;

	//腾讯云绑定的域名
	@NotBlank(message = "腾讯云绑定的域名不能为空", groups = QcloudGroup.class)
	@URL(message = "腾讯云绑定的域名格式不正确", groups = QcloudGroup.class)
	private String qcloudDomain;
	//腾讯云路径前缀
	private String qcloudPrefix;
	//腾讯云AppId
	@NotNull(message = "腾讯云AppId不能为空", groups = QcloudGroup.class)
	private Integer qcloudAppId;
	//腾讯云SecretId
	@NotBlank(message = "腾讯云SecretId不能为空", groups = QcloudGroup.class)
	private String qcloudSecretId;
	//腾讯云SecretKey
	@NotBlank(message = "腾讯云SecretKey不能为空", groups = QcloudGroup.class)
	private String qcloudSecretKey;
	//腾讯云BucketName
	@NotBlank(message = "腾讯云BucketName不能为空", groups = QcloudGroup.class)
	private String qcloudBucketName;
	//腾讯云COS所属地区
	@NotBlank(message = "腾讯云所属地区不能为空", groups = QcloudGroup.class)
	private String qcloudRegion;


	public String getLocalFilePrefix() {
		return localFilePrefix == null ? "" : localFilePrefix.trim();
	}

	public void setLocalFilePrefix(String localFilePrefix) {
		this.localFilePrefix = localFilePrefix;
	}

	public String getLocalFilePath() {
		return localFilePath;
	}

	public void setLocalFilePath(String localFilePath) {
		this.localFilePath = localFilePath;
	}

	public String getLocalFileMapping() {
		return localFileMapping;
	}

	public void setLocalFileMapping(String localFileMapping) {
		this.localFileMapping = localFileMapping;
	}

	public Boolean isFileCompress() {
		return "true".equalsIgnoreCase(fileCompress) ? true : false;
	}


	public Float getFloatQuality() {
		return Float.parseFloat(fileQuality);
	}

	public String getFileCompress() {
		return fileCompress;
	}

	public void setFileCompress(String fileCompress) {
		this.fileCompress = fileCompress;
	}

	public String getFileQuality() {
		return fileQuality;
	}

	public void setFileQuality(String fileQuality) {
		this.fileQuality = fileQuality;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getQiniuDomain() {
		return qiniuDomain;
	}

	public void setQiniuDomain(String qiniuDomain) {
		this.qiniuDomain = qiniuDomain;
	}

	public String getQiniuAccessKey() {
		return qiniuAccessKey;
	}

	public void setQiniuAccessKey(String qiniuAccessKey) {
		this.qiniuAccessKey = qiniuAccessKey;
	}

	public String getQiniuSecretKey() {
		return qiniuSecretKey;
	}

	public void setQiniuSecretKey(String qiniuSecretKey) {
		this.qiniuSecretKey = qiniuSecretKey;
	}

	public String getQiniuBucketName() {
		return qiniuBucketName;
	}

	public void setQiniuBucketName(String qiniuBucketName) {
		this.qiniuBucketName = qiniuBucketName;
	}

	public String getQiniuPrefix() {
		return qiniuPrefix;
	}

	public void setQiniuPrefix(String qiniuPrefix) {
		this.qiniuPrefix = qiniuPrefix;
	}

	public String getAliyunDomain() {
		return aliyunDomain;
	}

	public void setAliyunDomain(String aliyunDomain) {
		this.aliyunDomain = aliyunDomain;
	}

	public String getAliyunPrefix() {
		return aliyunPrefix;
	}

	public void setAliyunPrefix(String aliyunPrefix) {
		this.aliyunPrefix = aliyunPrefix;
	}

	public String getAliyunEndPoint() {
		return aliyunEndPoint;
	}

	public void setAliyunEndPoint(String aliyunEndPoint) {
		this.aliyunEndPoint = aliyunEndPoint;
	}

	public String getAliyunAccessKeyId() {
		return aliyunAccessKeyId;
	}

	public void setAliyunAccessKeyId(String aliyunAccessKeyId) {
		this.aliyunAccessKeyId = aliyunAccessKeyId;
	}

	public String getAliyunAccessKeySecret() {
		return aliyunAccessKeySecret;
	}

	public void setAliyunAccessKeySecret(String aliyunAccessKeySecret) {
		this.aliyunAccessKeySecret = aliyunAccessKeySecret;
	}

	public String getAliyunBucketName() {
		return aliyunBucketName;
	}

	public void setAliyunBucketName(String aliyunBucketName) {
		this.aliyunBucketName = aliyunBucketName;
	}

	public String getQcloudDomain() {
		return qcloudDomain;
	}

	public void setQcloudDomain(String qcloudDomain) {
		this.qcloudDomain = qcloudDomain;
	}

	public String getQcloudPrefix() {
		return qcloudPrefix;
	}

	public void setQcloudPrefix(String qcloudPrefix) {
		this.qcloudPrefix = qcloudPrefix;
	}

	public Integer getQcloudAppId() {
		return qcloudAppId;
	}

	public void setQcloudAppId(Integer qcloudAppId) {
		this.qcloudAppId = qcloudAppId;
	}

	public String getQcloudSecretId() {
		return qcloudSecretId;
	}

	public void setQcloudSecretId(String qcloudSecretId) {
		this.qcloudSecretId = qcloudSecretId;
	}

	public String getQcloudSecretKey() {
		return qcloudSecretKey;
	}

	public void setQcloudSecretKey(String qcloudSecretKey) {
		this.qcloudSecretKey = qcloudSecretKey;
	}

	public String getQcloudBucketName() {
		return qcloudBucketName;
	}

	public void setQcloudBucketName(String qcloudBucketName) {
		this.qcloudBucketName = qcloudBucketName;
	}

	public String getQcloudRegion() {
		return qcloudRegion;
	}

	public void setQcloudRegion(String qcloudRegion) {
		this.qcloudRegion = qcloudRegion;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
