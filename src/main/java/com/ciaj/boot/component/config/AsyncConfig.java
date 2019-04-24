package com.ciaj.boot.component.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @Description
 * @Author Ciaj.
 * @Date 2019/4/23 19:02
 * @Version 1.0
 */
@Configuration
@EnableAsync
public class AsyncConfig {
	@Value("${executor.corePoolSize}")
	private int corePoolSize = 10;
	@Value("${executor.maxPoolSize}")
	private int maxPoolSize = 200;
	@Value("${executor.queueCapacity}")
	private int queueCapacity = 10;
	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.initialize();
		return executor;
	}
}
