package com.example.satoken.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author hezhan
 * @Date 2020/3/23 15:42
 * 配置异步任务的线程池
 * 配置Executor，加入线程池配置
 * 然后在需要异步执行的类或者方法上加入@Async注解。
 */
@Configuration
@EnableAsync
public class AsyncTaskConfig {

    @Bean
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(20);
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.setKeepAliveSeconds(200);
        taskExecutor.setThreadNamePrefix("async-");
        // 设置拒绝策略：当pool已经达到max size的时候，如何处理新任务
        //AbortPolicy 丢弃任务，抛运行时异常
        //CallerRunsPolicy 执行任务 P:当线程池满以后，队列达到最大值时，异步先会变为同步执行，影响主线程性能，请结合业务场景，具体分析使用
        //DiscardPolicy 忽视，什么都不会发生
        //DiscardOldestPolicy 从队列中踢出最先进入队列（最后一个执行）的任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务都完成再继续销毁其他的Bean
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        taskExecutor.setAwaitTerminationSeconds(60);
        taskExecutor.initialize();
        return taskExecutor;
    }
}
