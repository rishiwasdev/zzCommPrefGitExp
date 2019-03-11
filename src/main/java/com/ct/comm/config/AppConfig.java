package com.ct.comm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.ct.comm.service.SchedulerService;

@Configuration
@EnableScheduling
@ComponentScan( "com.ct.comm" )
public class AppConfig {
	private final int POOL_SIZE = 4;

	@Bean
	public ThreadPoolTaskScheduler configureTasks() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(POOL_SIZE);
		taskScheduler.setThreadNamePrefix("comm-pool-");
		taskScheduler.initialize();
		return taskScheduler;
	}

	@Bean
	public SchedulerService schedulerService() {
		return new SchedulerService();
	}
}