package com.ciaj.comm.utils;

import com.ciaj.boot.component.config.shiro.ShiroUser;
import com.ciaj.comm.constant.DefaultConstant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/19 17:34
 * @Description:
 */
public class CommUtil {

    private static Logger logger = LoggerFactory.getLogger(CommUtil.class);

    public static ShiroUser getLoginUser() {
        try {
            return (ShiroUser) SecurityUtils.getSubject().getSession().getAttribute(DefaultConstant.LOGIN_USER);
        } catch (InvalidSessionException e) {
        }
        return null;
    }
}
