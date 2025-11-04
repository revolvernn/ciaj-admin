package com.ciaj.comm.utils;

import com.ciaj.boot.component.config.SpringContextUtils;
import com.ciaj.boot.component.config.shiro.ShiroUser;
import com.ciaj.boot.component.service.ShiroService;
import com.ciaj.comm.constant.DefaultConstant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
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
            Subject subject = SecurityUtils.getSubject();
            Object loginUser = subject.getSession().getAttribute(DefaultConstant.LOGIN_USER);
            if (loginUser == null) {
                Object principal = subject.getPrincipal();
                //
                ShiroService shiroService = SpringContextUtils.getBean(ShiroService.class);
                ShiroUser shiroUser = shiroService.selectShiroUserByAccount(principal.toString());
                subject.getSession().setAttribute(DefaultConstant.LOGIN_USER, shiroUser);
                return shiroUser;
            } else {
                return (ShiroUser) loginUser;
            }
        } catch (InvalidSessionException e) {
        }
        return null;
    }
}
