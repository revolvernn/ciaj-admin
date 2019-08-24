package com.ciaj.boot.component.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description
 * @Author Ciaj.
 * @Date 2019/4/23 19:02
 * @Version 1.0
 */
@Configuration
@EnableAsync
@Log4j2
public class ExecutorConfig {
	@Bean
	public Executor taskExecutor() {
		log.info("start asyncServiceExecutor");
		ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
		int corePoolSize = Runtime.getRuntime().availableProcessors();
		//核心线程数
		executor.setCorePoolSize(corePoolSize * 2);
		//最大线程数
		executor.setMaxPoolSize(corePoolSize * 2);
		//线程池维护线程所允许的空闲时间
		executor.setKeepAliveSeconds(300);
		//队列最大长度
		executor.setQueueCapacity(99999);
		//配置线程池中的线程的名称前缀
		executor.setThreadNamePrefix("async-service-");
		// rejection-policy：当pool已经达到max size的时候，并且队列已经满了，如何处理新任务
		// CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
		//DiscardPolicy: 直接丢弃
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();
		// 等待所有任务结束后再关闭线程池
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}
}
