package com.ciaj.boot.component.listener;

import com.ciaj.base.IAddServiceListener;
import com.ciaj.base.IUpdateServiceListener;
import com.ciaj.boot.component.service.ITaskSchedulerManager;
import com.ciaj.boot.modules.task.entity.po.TaskSchedulerPo;
import com.ciaj.comm.constant.TaskType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: wzn
 * @desc:
 * @date: 2026/6/3
 */
@Log4j2
@Component
public class TaskScheduledListener implements IUpdateServiceListener<TaskSchedulerPo>, IAddServiceListener<TaskSchedulerPo> {
    @Autowired
    ITaskSchedulerManager iTaskSchedulerManager;

    @Override
    public void postUpdate(TaskSchedulerPo taskSchedulerPo) {

        log.info("======TaskScheduledListener IUpdateServiceListener postUpdate======");
        if (taskSchedulerPo.getStatus().equals("0")) {
            iTaskSchedulerManager.startTask(
                    TaskType.valueOf(taskSchedulerPo.getType())
                    , taskSchedulerPo.getBeanName()
                    , taskSchedulerPo.getCron()
                    , taskSchedulerPo.getDelay());
        } else {
            iTaskSchedulerManager.cancelTask(taskSchedulerPo.getBeanName(), false);
        }
    }

    @Override
    public void postAdd(TaskSchedulerPo taskSchedulerPo) {
    }
}
