package com.ciaj.comm.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class ResponseUtils {

    /**
     * 客户端返回字符串
     * @param response
     * @param string
     * @return
     */
    public static void renderString(HttpServletResponse response, String string, String contentType) throws IOException {
            response.setContentType(contentType);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
    }
    public static void renderFile(HttpServletResponse response, String filePath, String contentType) throws IOException {
            if(!StringUtils.isEmpty(contentType)){
                response.setContentType(contentType);
            }
            response.setCharacterEncoding("utf-8");
            OutputStream stream = response.getOutputStream();
            File file = FileUtils.getFile(filePath);
            if(file == null || !file.exists()){
                throw new FileNotFoundException();
            }
            FileInputStream inputStream = new FileInputStream(file);
            renderinputStream(response,inputStream,contentType);
    }
    public static void renderinputStream(HttpServletResponse response, InputStream in, String contentType) throws IOException {
        if(!StringUtils.isEmpty(contentType)){
            response.setContentType(contentType);
        }
        response.setCharacterEncoding("utf-8");
        OutputStream stream = response.getOutputStream();

        byte[] data = StreamUtils.inputStreamToByteArray(in);
        in.close();
        stream.write(data);
        stream.flush();
        stream.close();
    }
}
