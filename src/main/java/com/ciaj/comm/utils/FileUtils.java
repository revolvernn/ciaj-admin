package com.ciaj.comm.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author: Ciaj.
 * @Date: 2019/2/25 14:00
 * @Description:
 */
public class FileUtils {

    /**
     * 上传文件到本地
     *
     * @param files
     * @param request
     * @param localPath :"upload/"
     *
     * @return
     */
    public static List<String> uploadFilesToLocal(MultipartFile[] files, HttpServletRequest request, String localPath) {
        List<String> filePaths = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            String filePath = uploadFileToLocal(multipartFile, request, localPath);
            if (filePath != null) {
                filePaths.add(filePath);
            }
        }
        return filePaths;
    }

    /**
     * 上传文件到本地
     *
     * @param file
     * @param request
     * @param localPath :"upload/"
     *
     * @return
     */
    public static String uploadFileToLocal(MultipartFile file, HttpServletRequest request, String localPath) {
        if(StringUtils.isBlank(localPath)){
            localPath = "upload/";
        }
        String filePath = null;
        String fileName = file.getOriginalFilename();
        if (fileName != null && fileName != "") {
            String path = request.getSession().getServletContext().getRealPath("/") + localPath;
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
            fileName = System.currentTimeMillis() + "_" + new Random().nextInt(1000) + fileF;//新的文件名

            //先判断文件是否存在
            File targetFile = new File(path, fileName);
            //如果文件夹不存在则创建
            if (!targetFile.exists() && !targetFile.isDirectory()) {
                targetFile.mkdirs();
            }
            try {
                file.transferTo(targetFile);
                filePath = request.getContextPath() + "/" + localPath + fileName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }
}
