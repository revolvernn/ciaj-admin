package com.ciaj.boot.modules.oss.web;

import com.ciaj.boot.modules.sys.entity.po.SysOssPo;
import com.ciaj.boot.modules.sys.service.SysOssService;
import com.ciaj.boot.modules.oss.cloud.OSSFactory;
import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.exception.BsRException;
import com.ciaj.comm.utils.FileTypeUtil;
import com.ciaj.comm.utils.ResponseUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;


/**
 * @Author: Ciaj.
 * @Date: 2019/2/20 16:06
 * @Description: oss 上传文件
 */
@Controller
@RequestMapping("oss")
public class FileController {

    @Autowired
    private SysOssService sysOssService;

    /**
     * 上传文件
     */
    @ResponseBody
    @RequestMapping("/upload")
    @RequiresPermissions("sys:oss:all")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file,@RequestParam(defaultValue = "upload") String prefix) throws Exception {
        if (file.isEmpty()) {
            throw new BsRException("上传文件不能为空");
        }

        //上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String url = OSSFactory.build().uploadSuffix(file.getBytes(), prefix, suffix);

        //保存文件信息
        SysOssPo ossEntity = new SysOssPo();
        ossEntity.setUrl(url);
        ossEntity.setSource(OSSFactory.getType());
        ossEntity.setType(FileTypeUtil.getFileType(url));
        sysOssService.insertDTO(ossEntity);
        return ResponseEntity.success("上传成功").put(url);
    }

    /**
     * TODO 未完善文件下载
     */
    @RequestMapping("/download")
    @RequiresPermissions("sys:oss:all")
    public void download(@RequestParam("url") String url) {
        OSSFactory.build().download(url);
    }


    /**
     * 文件访问
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/file/**"}, method = RequestMethod.GET)
    public void fileget(HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        String path = requestURI.substring(10);
        String contentType = null;
        byte[] data = null;
        try {
            data = OSSFactory.build().download(path);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        try {
            ResponseUtils.renderinputStream(response, new ByteArrayInputStream(data), contentType);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
