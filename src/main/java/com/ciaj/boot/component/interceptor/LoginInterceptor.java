package com.ciaj.boot.component.interceptor;

import com.ciaj.boot.component.config.shiro.ShiroUser;
import com.ciaj.comm.constant.DefaultConstant;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/19 19:28
 * @Description:
 */
@Log4j2
public class LoginInterceptor extends HandlerInterceptorAdapter {


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String basePath = request.getContextPath();
        String path = request.getRequestURI();
        log.info("basePath:" + basePath);
        log.info("path:" + path);

        if (!doLoginInterceptor(path, basePath)) {//是否进行登陆拦截
            return true;
        }

        //如果登录了，会把用户信息存进session
        ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getSession().getAttribute(DefaultConstant.LOGIN_USER);
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    }

    /**
     * 是否进行登陆过滤
     *
     * @param path
     * @param basePath
     *
     * @return
     */
    private boolean doLoginInterceptor(String path, String basePath) {
        path = path.substring(basePath.length());
        Set<String> notLoginPaths = new HashSet<>();
        //设置不进行登录拦截的路径：登录注册和验证码
        notLoginPaths.add("/index");
        notLoginPaths.add("/signin");
        notLoginPaths.add("/login");
        notLoginPaths.add("/doLogin");
        notLoginPaths.add("/register");
        notLoginPaths.add("/static");
        notLoginPaths.add("/public");
        notLoginPaths.add("/resource");
        notLoginPaths.add("/webjars");
        notLoginPaths.add("/asserts/");
        for (String notLoginPath : notLoginPaths) {
            if (path.contains(notLoginPath)) return false;
        }
        return true;
    }
}
