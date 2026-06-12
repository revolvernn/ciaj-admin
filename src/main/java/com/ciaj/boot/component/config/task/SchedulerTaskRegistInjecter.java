package com.ciaj.boot.component.config.task;

import com.ciaj.boot.component.service.ITaskScheduler;
import com.ciaj.boot.modules.task.entity.po.TaskSchedulerPo;
import com.ciaj.boot.modules.task.entity.vo.TaskSchedulerVo;
import com.ciaj.boot.modules.task.service.TaskSchedulerService;
import com.ciaj.comm.annotation.RegisterTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * @author: ciaj
 * @desc: 注册调度任务到数据库
 * @date: 2022/12/17
 */
@Slf4j
@Configuration
public class SchedulerTaskRegistInjecter implements BeanPostProcessor {
    @Autowired
    private TaskSchedulerService taskSchedulerService;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        RegisterTask anno = this.getAnnotation(bean);
        if (anno == null) {
            return bean;
        }

        if (!anno.enabled()) {
            return bean;
        }

        try {
            TaskSchedulerVo taskSchedulerVo = new TaskSchedulerVo();
            taskSchedulerVo.setBeanName(beanName);
            TaskSchedulerPo config = taskSchedulerService.selectOne(taskSchedulerVo);
            if (config != null) {
                // 不覆盖
                if (!anno.override()) {
                    return bean;
                }
                buildTaskScheduler(config, anno, beanName);
                taskSchedulerService.updateByPrimaryKeySelective(config);
                log.info(">>> 更新定时任务配置: {}", config);
                return bean;
            }

            // 新增加
            config = new TaskSchedulerPo();
            buildTaskScheduler(config, anno, beanName);
            taskSchedulerService.insert(config);

            log.info(">>> 新增定时任务配置: {}", config);
        } catch (Exception e) {
            log.warn("新增定时任务配置失败：- classpath:db/schema.scheduler-task.sql 未初始化 ");
        }

        return bean;
    }

    TaskSchedulerPo buildTaskScheduler(TaskSchedulerPo config, RegisterTask anno, String beanName) {
        config.setBeanName(beanName);
        config.setType(anno.type().name());
        config.setDelay(anno.delay());
        config.setStatus(anno.status());
        config.setCron(anno.cron());
        config.setName(anno.name());
        config.setRemark(anno.remark());
        return config;
    }

    RegisterTask getAnnotation(Object bean) {
        if (!(bean instanceof ITaskScheduler)) {
            return null;
        }
        try {
            Method method = bean.getClass().getMethod("run");
            return method.getAnnotation(RegisterTask.class);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

}
