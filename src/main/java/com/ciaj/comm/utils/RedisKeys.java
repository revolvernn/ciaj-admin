package com.ciaj.comm.utils;

/**
 * @Author: Ciaj.
 * @Date: 2018/12/7 09:59
 * @Description:
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }

    public static String getShiroSessionKey(String key){
        return "sessionid:" + key;
    }
}
