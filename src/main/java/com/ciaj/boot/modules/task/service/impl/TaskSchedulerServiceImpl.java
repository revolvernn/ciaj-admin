package com.ciaj.boot.modules.task.service.impl;

import com.ciaj.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciaj.boot.modules.task.mapper.TaskSchedulerMapper;
import com.ciaj.boot.modules.task.entity.po.TaskSchedulerPo;
import com.ciaj.boot.modules.task.service.TaskSchedulerService;
import com.ciaj.boot.modules.task.entity.vo.TaskSchedulerVo;
import com.ciaj.boot.modules.task.entity.dto.TaskSchedulerDto;

/**
 * @Author: Ciaj.
 * @Date: 2026-06-02 21:57:07
 * @Description: www.ciaj.com gen service  实现
 */
@Service
public class TaskSchedulerServiceImpl extends AbstractService<TaskSchedulerPo, TaskSchedulerDto, TaskSchedulerVo> implements TaskSchedulerService {

    @Autowired
    private TaskSchedulerMapper taskSchedulerMapper;
}
