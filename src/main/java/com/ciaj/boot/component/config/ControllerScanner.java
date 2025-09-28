package com.ciaj.boot.component.config;

import com.ciaj.boot.modules.sys.entity.po.SysPermissionPo;
import com.ciaj.comm.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: wzn
 * @desc:
 * @date: 2025/9/8
 */
@Slf4j
@Component
public class ControllerScanner implements CommandLineRunner {

    @Autowired
    private ApplicationContext context;

    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(Controller.class);
        Map p = new HashMap();
        beansWithAnnotation.forEach((beanName, bean) -> {
            String name = bean.getClass().getName();
            String getSimpleName = bean.getClass().getSimpleName();

            String[] ss = name.split("\\$\\$");
            try {
                log.info("{} {}", name, getSimpleName);
                Class<?> aClass1 = Class.forName(ss[0]);
                Api api = aClass1.getAnnotation(Api.class);
                RequestMapping requestMapping = aClass1.getAnnotation(RequestMapping.class);
                if (api != null && requestMapping != null) {
                    Map pp = new HashMap();
                    Method[] methods = aClass1.getDeclaredMethods();
                    for (Method method : methods) {
                        Permission permission = warp(method, requestMapping);
                        if (permission != null) {
                            pp.put(permission.url, permission);
                        }
                    }

                    p.put(StringUtil.getJoinString(",", api.tags()), pp);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });
        p.forEach((name, map) -> {
            log.info("===={}", name);
            Map<String, Permission> permissionMap = (Map<String, Permission>) map;
            permissionMap.forEach((code, permission) -> {
                log.info("===={}", permission);

            });
        });
    }

    public Permission warp(Method method, RequestMapping requestMapping) {
        Annotation[] annotations = method.getAnnotations();
        Permission permission = new Permission();
        for (Annotation annotation : annotations) {
            if (annotation instanceof ApiOperation) {
                ApiOperation l = (ApiOperation) annotation;
                permission.setName(l.value());
            }
            if (annotation instanceof GetMapping) {
                GetMapping l = (GetMapping) annotation;
                permission.setUrl(StringUtil.getJoinString("/", requestMapping.value(), l.value()));
            }
            if (annotation instanceof PostMapping) {
                PostMapping l = (PostMapping) annotation;
                permission.setUrl(StringUtil.getJoinString("/", requestMapping.value(), l.value()));
            }
            if (annotation instanceof PutMapping) {
                PutMapping l = (PutMapping) annotation;
                permission.setUrl(StringUtil.getJoinString("/", requestMapping.value(), l.value()));
            }
            if (annotation instanceof DeleteMapping) {
                DeleteMapping l = (DeleteMapping) annotation;
                permission.setUrl(StringUtil.getJoinString("/", requestMapping.value(), l.value()));
            }
            if (annotation instanceof RequestMapping) {
                RequestMapping l = (RequestMapping) annotation;
                permission.setUrl(StringUtil.getJoinString("/", requestMapping.value(), l.value()));
            }
            if (annotation instanceof RequiresPermissions) {
                RequiresPermissions l = (RequiresPermissions) annotation;
                permission.setCode(StringUtil.getJoinString(l.value()));
            }
        }
        return permission.getName() != null ? permission : null;
    }

    @Data
    public class Permission {
        private String name;
        private String code;
        private String url;

        @Override
        public String toString() {
            return "{name='" + name + '\'' +
                    ", code='" + code + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
