package com.ciaj.comm.annotation;


import com.ciaj.comm.constant.LogTypeEnum;

import java.lang.annotation.*;

/**
 * @Author: Ciaj.
 * @Date: 2019/1/10 17:06
 * @Description: 操作日志
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {
	/**
	 * 是否添加到数据库
	 *
	 * @return
	 */
	boolean isInsert() default true;

	/**
	 * 操作模块
	 *
	 * @return
	 */
	String operation() default "";

	/**
	 * 操作日志内容
	 *
	 * @return
	 */
	String content() default "";

	/**
	 * 日志类型
	 *
	 * @return
	 */
	LogTypeEnum type() default LogTypeEnum.OPERATION;
}
