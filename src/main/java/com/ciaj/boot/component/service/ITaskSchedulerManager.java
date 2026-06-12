package com.ciaj.boot.component.service;

import com.ciaj.comm.constant.TaskType;

/**
 * @author: ciaj
 * @desc: 动态任务
 * @date: 2022/12/17
 */
public interface ITaskSchedulerManager {
    /**
     * 直接执行定时任务
     *
     * @param beanName
     */
    void runTask(String beanName);

    /**
     * 直接执行定时任务
     *
     * @param beanName
     * @param updateDB 是否更新库
     */
    void runTask(String beanName, Boolean updateDB);

    /**
     * 开始定时任务
     *
     * @param type
     * @param beanName
     * @param cron
     * @param delay
     * @return
     */
    boolean startTask(TaskType type, String beanName, String cron, long delay);

    /**
     * 取消定时任务
     *
     * @param beanName
     * @return
     */
    boolean cancelTask(String beanName);

    /**
     * 取消定时任务
     *
     * @param beanName
     * @param updateDB 是否更新库
     * @return
     */
    boolean cancelTask(String beanName, Boolean updateDB);

}
