package com.ciaj.boot.component.config.task;

import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.config.Task;

import java.util.concurrent.ScheduledFuture;

/**
 * @author: ciaj
 * @desc:
 * @date: 2022/12/17
 */
@Data
public class DyTaskScheduler {
    /**
     * 任务
     */
    private final Task task;
    /**
     * 调度
     */
    @Nullable
    volatile ScheduledFuture<?> future;

    public DyTaskScheduler(Task task) {
        this.task = task;
    }
}
