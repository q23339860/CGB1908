package com.cy.pj.common.config;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Configuration
@ConfigurationProperties("async-thread-pool")
public class SpringAsyncConfig implements AsyncConfigurer {
	private int corePoolSize = 20;
	private int maximumPoolSize = 1000;
	private int keepAliveTime = 30;
	private int queueCapacity = 200;
	private ThreadFactory threadFactory = new ThreadFactory() {
		private AtomicInteger at = new AtomicInteger(1000);

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "db-async-thread-" + at.getAndIncrement());
		}
	};

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maximumPoolSize);
		executor.setKeepAliveSeconds(keepAliveTime);
		executor.setQueueCapacity(queueCapacity);
		;
		executor.setRejectedExecutionHandler((Runnable r, ThreadPoolExecutor exe) -> {
			log.warn("当前任务线程池队列已满。");
		});
		executor.initialize();
		return executor;
	}

	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new AsyncUncaughtExceptionHandler() {

			@Override
			public void handleUncaughtException(Throwable ex, Method method, Object... params) {
				log.error("线程池执行任务发生未知异常。",ex);

			}
		};
	}
}
