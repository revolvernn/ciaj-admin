package com.ciaj.boot.modules.oss.web;

import com.ciaj.boot.modules.sys.entity.po.SysOssPo;
import com.ciaj.boot.modules.sys.service.SysOssService;
import com.ciaj.boot.modules.oss.cloud.OSSFactory;
import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.exception.BsRException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


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
        sysOssService.insertDTO(ossEntity);
        return ResponseEntity.success().put(url);
    }

    /**
     * 上传文件
     */
    @RequestMapping("/download")
    @RequiresPermissions("sys:oss:all")
    public void download(@RequestParam("url") String url) {
        OSSFactory.build().download(url);
    }
}
