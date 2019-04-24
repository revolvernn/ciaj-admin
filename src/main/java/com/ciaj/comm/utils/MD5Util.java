package com.ciaj.comm.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/4 11:08
 * @Description:
 */
public class MD5Util {
    /**
     * MD5加密
     *
     * @param data 待加密数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeMD5(String data) throws Exception {

        // 执行消息摘要
        return DigestUtils.md5(data);
    }

    /**
     * MD5加密
     *
     * @param data 待加密数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static String encodeMD5Hex(String data) {
        // 执行消息摘要
        return DigestUtils.md5Hex(data);
    }
}
