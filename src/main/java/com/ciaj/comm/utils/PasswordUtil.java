/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.ciaj.comm.utils;

import com.ciaj.comm.pwd.PasswordEntity;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.UUID;


/**
 * @Author: Ciaj.
 * @Date: 2018/6/19 10:31
 * @Description: 密码生成工具类
 */
public class PasswordUtil {
    public  static String ALGORITHM_NAME_MD5 = "md5";
    public  static int HASH_ITERATIONS = 1024;

    public static PasswordEntity getPassword(String password, String salt) {
        return new PasswordEntity(new SimpleHash(ALGORITHM_NAME_MD5, password, ByteSource.Util.bytes(salt), HASH_ITERATIONS).toHex(), new String(salt));
    }

    public static PasswordEntity getPassword(String password) {

        byte[] salt = new byte[0];
        try {
            salt = MD5Util.encodeMD5Hex(UUID.randomUUID().toString().replace("-","")).getBytes();
            return new PasswordEntity(new SimpleHash(ALGORITHM_NAME_MD5, password, ByteSource.Util.bytes(salt), HASH_ITERATIONS).toHex(), new String(salt, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PasswordEntity();
    }

    public static void main(String[] args) {
        PasswordEntity password = getPassword("123456"); //24205511f720bbb5f675b5e12deb5df9 3b5e0f4d75593b84806b8e6b5a681e5d
//        PasswordEntity password = getPassword("123456","24205511f720bbb5f675b5e12deb5df9"); //24205511f720bbb5f675b5e12deb5df9
        System.out.println("password：" + password.getPassword() + " salt：" + password.getSalt());
    }
}
