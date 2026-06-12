package com.ciaj.boot.component.service.impl;

import com.ciaj.boot.component.config.task.DyTaskScheduler;
import com.ciaj.boot.component.service.ITaskScheduler;
import com.ciaj.boot.component.service.ITaskSchedulerManager;
import com.ciaj.boot.modules.task.entity.dto.TaskSchedulerDto;
import com.ciaj.boot.modules.task.entity.po.TaskSchedulerPo;
import com.ciaj.boot.modules.task.entity.vo.TaskSchedulerVo;
import com.ciaj.boot.modules.task.service.TaskSchedulerService;
import com.ciaj.comm.constant.TaskType;
import com.ciaj.comm.exception.BsRException;
import com.ciaj.comm.utils.CalendarUtils;
import com.ciaj.comm.utils.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.FixedDelayTask;
import org.springframework.scheduling.config.FixedRateTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Function;


/**
 * @author: ciaj
 * @desc:
 * @date: 2022/12/17
 */
@Service
@Slf4j
public class ITaskSchedulerManagerImpl implements ITaskSchedulerManager, CommandLineRunner, ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 调度任务
     */
    Map<String, DyTaskScheduler> taskMap = new ConcurrentHashMap<>();
    /**
     * 应用启动任务开关，默认true
     */
    @Value("${ciaj.scheduler.enabled:true}")
    private Boolean enabled;

    @Autowired(required = false)
    private ThreadPoolTaskScheduler taskScheduler;

    @Override
    public boolean cancelTask(String beanName) {
        return cancelTask(beanName, true);
    }

    @Override
    public boolean cancelTask(String beanName, Boolean updateDB) {
        log.info("取消定时调度任务：{}", beanName);
        if (StringUtil.isBlank(beanName)) {
            return false;
        }
        if (updateDB) {
            TaskSchedulerPo taskScheduler = new TaskSchedulerDto();
            taskScheduler.setBeanName(beanName);
            TaskSchedulerPo taskSchedulerPo = taskSchedulerService.selectOne(taskScheduler);
            taskSchedulerPo.setStatus("1");
            taskSchedulerService.updateByPrimaryKey(taskSchedulerPo);
        }
        DyTaskScheduler task = taskMap.remove(beanName);
        if (task != null && task.getFuture() != null) {
            task.getFuture().cancel(true);
            return true;
        }
        return false;
    }

    @Override
    public void runTask(String beanName) {
        runTask(beanName, true);
    }

    @Override
    public void runTask(String beanName, Boolean updateDB) {
        log.info("执行定时调度任务：{}", beanName);
        ITaskScheduler target = null;
        try {
            target = (ITaskScheduler) context.getBean(beanName);
        } catch (Throwable e) {
            throw new BsRException(String.format("无法获取bean信息: %s, 无法执行任务: %s", beanName, e.getMessage()));
        }
        if (updateDB) {
            TaskSchedulerPo taskScheduler = new TaskSchedulerDto();
            taskScheduler.setBeanName(beanName);
            TaskSchedulerPo taskSchedulerPo = taskSchedulerService.selectOne(taskScheduler);
            taskSchedulerPo.setStatus("0");
            taskSchedulerService.updateByPrimaryKey(taskSchedulerPo);
        }
        // 执行
        target.run();
    }


    @Override
    public boolean startTask(TaskType type, String beanName, String cron, long delay) {
        log.info("开始启动定时调度任务, beanName: {},type: {},cron: {}, delay: {}ms-{}", beanName, type.name(), cron, delay, CalendarUtils.formatTime(delay));
        if (taskScheduler == null) {
            log.warn("初始化定时调度任务，失败，未配置 org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler Bean ...");
            return false;
        }
        ITaskScheduler target = null;
        try {
            target = (ITaskScheduler) context.getBean(beanName);
        } catch (Throwable e) {
            throw new BsRException(String.format("无法获取bean信息: %s, 无法执行任务: %s", beanName, e.getMessage()));
        }

        Method method = null;
        try {
            method = target.getClass().getMethod("run");
        } catch (NoSuchMethodException e) {
            throw new BsRException("无法获取run方法, 无法执行任务:" + e.getMessage());
        }

        try {
            TaskCommand command = new TaskCommand();
            command.setBeanName(beanName);
            command.setCron(cron);
            command.setDelay(delay);
            command.setMethod(method);
            command.setTarget(target);

            Map<String, Function<TaskCommand, Boolean>> taskMap = new HashMap<>();
            taskMap.put(TaskType.cron.name(), this::initCron);
            taskMap.put(TaskType.fixedDelay.name(), this::initFixedDelay);
            taskMap.put(TaskType.fixedRate.name(), this::initFixedRate);

            return Optional.ofNullable(taskMap.get(type.name()))
                    .map(f -> f.apply(command))
                    .orElseThrow(() -> new BsRException("调度任务类型不正确"));
        } catch (Throwable e) {
            log.info("配置调度任务异常:{}", e.getMessage());
        }
        return true;
    }

    @Data
    public static class TaskCommand {
        private String beanName;
        private String cron;
        private long delay;
        private ITaskScheduler target;
        private Method method;
    }

    /**
     * 设置定时任务执行的时间间隔，该值为当前任务启动时间与下次任务启动时间之差；
     * 指定间隔时间执行一次任务，间隔时间为前一次执行开始到下次任务开始时间
     *
     * @param command
     * @return
     */
    private Boolean initFixedRate(TaskCommand command) {
        FixedRateTask task = new FixedRateTask(createRunnable(command.getTarget(), command.getMethod()),
                0, command.getDelay());

        DyTaskScheduler schedulerTask = new DyTaskScheduler(task);
        ScheduledFuture<?> future = taskScheduler.scheduleAtFixedRate(task.getRunnable(), task.getInitialDelay());
        schedulerTask.setFuture(future);

        cancelTask(command.getBeanName(), false);
        taskMap.put(command.getBeanName(), schedulerTask);
        log.info("启动定时调度任务完成 initFixedRate, beanName: {}", command.getBeanName());

        return true;
    }

    /**
     * 设置定时任务执行的时间间隔，该值为当前任务结束时间与下次任务启动时间之差；
     * 指定间隔时间执行一次任务，间隔时间为前一次任务完成到下一次开始时间
     *
     * @param command
     * @return
     */
    private Boolean initFixedDelay(TaskCommand command) {
        FixedDelayTask task = new FixedDelayTask(createRunnable(command.getTarget(), command.getMethod()),
                0, command.getDelay());

        DyTaskScheduler taskInfo = new DyTaskScheduler(task);
        ScheduledFuture<?> future = taskScheduler.scheduleWithFixedDelay(task.getRunnable(), task.getInitialDelay());
        taskInfo.setFuture(future);

        cancelTask(command.getBeanName(), false);
        taskMap.put(command.getBeanName(), taskInfo);
        log.info("启动定时调度任务完成 initFixedDelay, beanName: {}", command.getBeanName());
        return true;
    }

    /**
     * 通过cron表达式来设置定时任务启动时间，在Cron Generator网站可以直接生成cron表达式。
     *
     * @param command
     * @return
     */
    private Boolean initCron(TaskCommand command) {
        CronTask cronTask = new CronTask(createRunnable(command.getTarget(), command.getMethod()),
                new CronTrigger(command.getCron(), TimeZone.getTimeZone(ZoneId.systemDefault())));

        DyTaskScheduler taskInfo = new DyTaskScheduler(cronTask);
        ScheduledFuture<?> future = taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
        taskInfo.setFuture(future);

        cancelTask(command.getBeanName(), false);
        taskMap.put(command.getBeanName(), taskInfo);
        log.info("启动定时调度任务完成 initFixedDelay, beanName: {}", command.getBeanName());
        return true;
    }

    /**
     * @param target
     * @param method
     * @return
     */
    private Runnable createRunnable(Object target, Method method) {
        Method invocableMethod = AopUtils.selectInvocableMethod(method, target.getClass());
        return new ScheduledMethodRunnable(target, invocableMethod);
    }


    @Autowired
    private TaskSchedulerService taskSchedulerService;

    @Override
    public void run(String... args) throws Exception {
        log.debug("初始化定时调度任务，默认自动启动定时任务：{}", enabled);
        if (taskScheduler == null) {
            log.warn("初始化定时调度任务，失败，未配置 org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler Bean ...");
            return;
        }
        try {
            TaskSchedulerVo taskSchedulerVo = new TaskSchedulerVo();
            taskSchedulerVo.setStatus("0");

            List<TaskSchedulerPo> taskConfigs = taskSchedulerService.selectList(taskSchedulerVo);
            taskConfigs.forEach(config -> {
                log.info("初始化定时调度任务: {}", config);
                try {
                    if (enabled) {
                        startTask(TaskType.valueOf(config.getType()), config.getBeanName(), config.getCron(), config.getDelay());
                    } else {
                        config.setStatus("1");
                        taskSchedulerService.updateByPrimaryKey(config);
                    }
                } catch (BsRException bx) {
                    config.setStatus("1");
                    taskSchedulerService.updateByPrimaryKey(config);
                    log.error("初始化定时调度任务失败: {}", bx.getMessage());
                } catch (Throwable e) {
                    log.error("初始化定时调度任务失败: {}", e.getMessage());
                }
            });
        } catch (Exception e) {
            log.warn("初始化定时调度任务失败：- classpath:task.sql 未初始化 ");
        }
    }

}
