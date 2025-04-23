package com.ruoyi.web.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 参数修改线程池配置
 */
@Configuration
@EnableAsync
public class SlotThreadPoolConfigure {
    // 设置核心线程数
    @Value("${spring.task.pool.corePoolSize}")
    private int corePoolSize;
    // 设置最大线程数
    @Value("${spring.task.pool.maxPoolSize}")
    private int maxPoolSize;
    // 设置线程活跃时间（秒）
    @Value("${spring.task.pool.keepAliveSeconds}")
    private int keepAliveSeconds;
    // 设置队列容量
    @Value("${spring.task.pool.queueCapacity}")
    private int queueCapacity;

    @Bean(name="SlotThreadPool")
    public Executor syncTaskAsyncPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setQueueCapacity(queueCapacity);
        // 设置默认线程名称
        executor.setThreadNamePrefix("SaveSlotThread-");
        // 设置拒绝策略
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }
}
