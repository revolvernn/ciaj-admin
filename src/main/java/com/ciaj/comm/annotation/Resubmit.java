package com.ciaj.comm.annotation;

import com.ciaj.comm.constant.ParamTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 表单重复提交
 * @Author Ciaj.
 * @Date 2019/4/16 16:33
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Resubmit {
	ParamTypeEnum value() default ParamTypeEnum.json;
}
