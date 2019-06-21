package com.ciaj.comm.utils;

import com.ciaj.boot.component.config.SpringContextUtils;
import com.ciaj.boot.modules.sys.service.SysConfigService;
import com.ciaj.comm.constant.DefaultConfigConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 文件类型工具
 * @Author Ciaj.
 * @Date 2019/5/29 18:17
 * @Version 1.0
 */
public class FileTypeUtil {
	//{"psd":"image","aac":"audio","tiff":"image","mod":"audio","bmp":"image","gif":"image","mid":"audio","wma":"audio","vqf":"audio","ogg":"audio","tga":"image","pcd":"image","wmf":"image","rmvb":"video","dxf":"image","avi":"video","mov":"video","navi":"video","md":"audio","ape":"audio","jpeg":"image","mkv":"video","m4a":"audio","3gp":"video","jpg":"image","wmv":"video","cd":"audio","pcx":"image","svg":"image","png":"image","ai":"image","eps":"image","raw":"image","f4v":"video","wav":"audio","webm":"video","fpx":"image","ra":"audio","cdr":"image","mp4":"video","mp3":"audio","asf":"audio","exif":"image","ufo":"image"}
	public  static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

	static {
		SysConfigService sysConfigService = SpringContextUtils.getBean(SysConfigService.class);
		FileTypeUtil.FILE_TYPE_MAP = sysConfigService.getConfigObject(DefaultConfigConstant.DEFAULT_FILE_TYPE_CONFIG_KEY, HashMap.class);
	}
	/**
	 * 获取文件类型
	 *
	 * @param url
	 * @return
	 */
	public static String getFileType(String url) {
		if (StringUtil.isBlank(url)) return null;
		String fileTyle = url.substring(url.lastIndexOf(".") + 1, url.length());
		String key = FILE_TYPE_MAP.get(fileTyle.toLowerCase());
		if (StringUtil.isNotBlank(key)) {
			return FileType.getEnum(key).getType();
		}
		return FileType.other.getType();
	}

	/**
	 * 获取文件类型名称
	 *
	 * @param url
	 * @return
	 */
	public static String getFileName(String url) {
		if (StringUtil.isBlank(url)) return null;
		String fileTyle = url.substring(url.lastIndexOf(".") + 1, url.length());
		String key = FILE_TYPE_MAP.get(fileTyle.toLowerCase());
		if (StringUtil.isNotBlank(key)) {
			return FileType.getEnum(key).getName();
		}
		return FileType.other.getName();
	}

	/**
	 * 文件类型
	 */
	enum FileType {
		image("image", "图片"),
		video("video", "视频"),
		audio("audio", "音频"),
		other("otherFile", "其他文件");
		private String type;
		private String name;

		FileType(String value, String name) {
			this.type = value;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public String getType() {
			return type;
		}

		public static FileType getEnum(String key) {
			for (FileType fileType : FileType.values()) {
				if (fileType.getName().equalsIgnoreCase(key) || fileType.getType().equalsIgnoreCase(key)) {
					return fileType;
				}
			}
			return null;
		}
	}

}
