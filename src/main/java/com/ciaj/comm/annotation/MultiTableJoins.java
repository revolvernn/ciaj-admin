package com.ciaj.comm.annotation;

import com.ciaj.base.Mapper;

import java.lang.annotation.*;

/**
 * @Author: Ciaj.
 * @Date: 2019/3/6 09:13
 * @Description: 多表连接-缓存处理 @MultiTableJoins(mappers = {SysDictMapper.class, SysAreaMapper.class}) 使用时方法以 MultiTable结尾：selectMultiTable
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MultiTableJoins {
	/**
	 * 需要清理缓存的：Mapper class
	 *
	 * @return
	 */
	Class<? extends Mapper>[] mappers() default {};
}
