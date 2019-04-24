package com.ciaj.boot.component.job;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

/**
 * @Description 定时
 * @Author Ciaj.
 * @Date 2019/4/23 19:00
 * @Version 1.0
 */
@Log4j2
@Component
public class ScheduledService {
	@Autowired
	Executor  executor;


	@Scheduled(cron = "* * * * * ? ")
	public void cron() {
		log.info("=====>>>>>每秒使用cron  {}", System.currentTimeMillis());

		executor.execute(new Runnable() {
			@Override
			public void run() {
				log.info("=====>>>>>每秒使用execute  {}", System.currentTimeMillis());
			}
		});
	}
}
