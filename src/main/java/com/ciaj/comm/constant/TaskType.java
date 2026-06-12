package com.ciaj.comm.constant;
/**
 * <p>
 * 任务调度类型
 * </p>
 *
 * @author ciaj
 * @since 2022-12-17 17:39:36
 */
public enum TaskType {
    fixedRate, //设置定时任务执行的时间间隔，该值为当前任务启动时间与下次任务启动时间之差；
    fixedDelay,//设置定时任务执行的时间间隔，该值为当前任务结束时间与下次任务启动时间之差；
    cron; //通过cron表达式来设置定时任务启动时间，在Cron Generator网站可以直接生成cron表达式。
}
