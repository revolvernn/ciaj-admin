package com.ciaj.comm.annotation;

import com.ciaj.comm.constant.TaskType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在run方法上添加该注解，启动后自动将信息注入到数据库中
 *
 * @author ciaj
 * @since 2022-12-17 17:39:36
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterTask {

    /**
     * 是否启用定时任务信息注入到数据中
     *
     * @return
     */
    TaskType type();

    /**
     * cron表达式,type为 cron时必填
     *
     * @return
     */
    String cron() default "";

    /**
     * 间隔毫秒，type 为 fixedRate,fixedDelay 时必填
     *
     * @return
     */
    long delay() default -0L;

    /**
     * 是否启用定时任务信息注入到数据中
     *
     * @return
     */
    boolean enabled() default true;

    /**
     * 每次重启，覆盖掉数据库中的TASK记录
     */
    boolean override() default false;

    /**
     * 任务名称
     *
     * @return
     */
    String name();

    /**
     * 备注
     *
     * @return
     */
    String remark() default "";

    /**
     * 状态: 0 启用 1 禁用
     *
     * @return
     */
    String status() default "0";

}