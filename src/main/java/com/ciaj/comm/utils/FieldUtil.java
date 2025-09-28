package com.ciaj.comm.utils;

import com.ciaj.boot.modules.sys.web.SysUserController;
import com.ciaj.comm.annotation.OperationLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wzn
 * @desc:
 * @date: 2025/8/24
 */
@Slf4j
public class FieldUtil {

    public static Field getField(String fieldName, Class record) {
        Field field = null;
        try {
            field = record.getSuperclass().getDeclaredField(fieldName);
            if (field == null) {
                field = record.getDeclaredField(fieldName);
            }
        } catch (NoSuchFieldException e) {
            log.error(e.getMessage(), e);
        }
        return field;
    }

    public static Field getClassField(String fieldName, Class record) {
        try {
            return record.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取这个类的所有父类
     *
     * @param clazz
     * @return
     */
    public static List<Class<?>> getSuperClass(Class<?> clazz) {
        List<Class<?>> clazzs = new ArrayList<Class<?>>();
        clazzs.add(clazz);
        Class<?> suCl = clazz.getSuperclass();
        while (suCl != null) {
            clazzs.add(suCl);
            suCl = suCl.getSuperclass();
        }
        return clazzs;
    }

    /**
     * 获取这个类和所有父类方法的注解
     *
     * @param clazz
     * @param methodName
     * @return
     */
    public static <T extends Annotation> List<T> getClassAllAnnotations(Class<?> clazz, String methodName, Class<T> annotationClass) {
        List<T> list = new ArrayList<T>();
        List<Class<?>> clazzs = new ArrayList<Class<?>>();
        clazzs.add(clazz);
        try {
            Method classMethod = clazz.getMethod(methodName);
            T t = classMethod.getAnnotation(annotationClass);
            if (t != null) {
                list.add(t);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        Class<?> suCl = clazz.getSuperclass();
        while (suCl != null) {
            try {
                Method superclassMethod = suCl.getMethod(methodName);
                T tt = superclassMethod.getAnnotation(annotationClass);
                if (tt != null) {
                    list.add(tt);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }

            clazzs.add(suCl);
            suCl = suCl.getSuperclass();

        }
        return list;
    }

    /**
     * 获取这个类和所有父类方法的注解的第一个
     *
     * @param clazz
     * @param methodName
     * @return
     */
    public static <T extends Annotation> T getClassFirstAnnotation(Class<?> clazz, String methodName, Class<T> annotationClass) {
        List<T> superClassAllAnnotations = getClassAllAnnotations(clazz, methodName, annotationClass);
        return CollectionUtil.isNotEmpty(superClassAllAnnotations) ? superClassAllAnnotations.get(0) : null;
    }

    public static void main(String[] args) {
        String s = "com.ciaj.boot.modules.sys.web.CommController$$EnhancerBySpringCGLIB$$ae687896";
        String[] $$s = s.split("\\$\\$");


        Class<SysUserController> sysUserControllerClass = SysUserController.class;
        RequestMapping requestMapping = sysUserControllerClass.getAnnotation(RequestMapping.class);
        Api api = sysUserControllerClass.getAnnotation(Api.class);
        log.info("api= {} requestMapping : {}", api.tags(), requestMapping.value());
        Method[] methods = sysUserControllerClass.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            log.info("name={} {}", method.getName(), method.getAnnotations().length);
            for (Annotation annotation : annotations) {
                if (annotation instanceof ApiOperation) {
                    ApiOperation l = (ApiOperation) annotation;
                    log.info("ApiOperation= {}", l.value());
                }
                if (annotation instanceof GetMapping) {
                    GetMapping l = (GetMapping) annotation;
                    log.info("GetMapping= {}", StringUtil.getJoinString("/", requestMapping.value(), l.value()));
                }
                if (annotation instanceof PostMapping) {
                    PostMapping l = (PostMapping) annotation;
                    log.info("PostMapping= {}", StringUtil.getJoinString("/", requestMapping.value(), l.value()));
                }
                if (annotation instanceof PutMapping) {
                    PutMapping l = (PutMapping) annotation;
                    log.info("PutMapping= {}", StringUtil.getJoinString("/", requestMapping.value(), l.value()));
                }
                if (annotation instanceof DeleteMapping) {
                    DeleteMapping l = (DeleteMapping) annotation;
                    log.info("DeleteMapping= {}", StringUtil.getJoinString("/", requestMapping.value(), l.value()));
                }
                if (annotation instanceof RequestMapping) {
                    RequestMapping l = (RequestMapping) annotation;
                    log.info("RequestMapping= {}", StringUtil.getJoinString("/", requestMapping.value(), l.value()));
                }
                if (annotation instanceof RequiresPermissions) {
                    RequiresPermissions l = (RequiresPermissions) annotation;
                    log.info("RequiresPermissions= {}", l.value());
                }
            }
        }
    }
}
