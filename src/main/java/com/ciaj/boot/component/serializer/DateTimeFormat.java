package com.ciaj.boot.component.serializer;

/**
 * @author: wzn
 * @desc:
 * @date: 2021/12/8
 */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 日期 格式化 使用在GET方法上
 * @Date 2021/12/08 17:27
 * @Created by ciaj.
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@JsonSerialize(using = DT2Serializer.class)
public @interface DateTimeFormat {
    public String pattern() default "yyyy-MM-dd HH:mm:ss";
}
