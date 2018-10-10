package com.vz.paas.core.config;

import java.util.concurrent.Executor;

import javax.annotation.Resource;

import com.vz.paas.config.properties.PaasProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 异步任务配置类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 20:13:33
 */
@Slf4j
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncTaskExecutorConfiguration implements AsyncConfigurer {

    @Resource
    private PaasProperties properties;

    @Override
    @Bean("taskExecutor")
    public Executor getAsyncExecutor() {
        log.debug("creating async task executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getTask().getCorePoolSize());
        executor.setMaxPoolSize(properties.getTask().getMaxPoolSize());
        executor.setQueueCapacity(properties.getTask().getQueueCapacity());
        executor.setKeepAliveSeconds(properties.getTask().getKeepAliveSeconds());
        executor.setThreadNamePrefix(properties.getTask().getThreadNamePrefix());
        return new ExceptionHandlingAsyncTaskExecutor(executor);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
