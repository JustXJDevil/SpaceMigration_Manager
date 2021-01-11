package com.future.sm.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 从配置文件中获取信息,通过set方法赋值给类的属性
 */
@ConfigurationProperties("async-thread-pool")
@Configuration
@Slf4j
public class SpringAsyncConfig implements AsyncConfigurer {
    private Integer corePoolSize;
    private Integer maxPoolSize;
    private Integer keepAliveSeconds;
    private Integer queueCapacity;

    private ThreadFactory threadFactory = new ThreadFactory() {
        //线程安全，目的用它来给线程起名字
        private AtomicLong atomicLong = new AtomicLong(1);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"SP-manager-thread-"+atomicLong.getAndIncrement());
        }
    };
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        /**
         * 线程池的常驻核心线程数
         * 1.当有请求任务时就安排池中的线程去执行
         * 2.当池中线程数达到corePoolSize后，就会把到达的任务放到缓存队列中
         */
        poolTaskExecutor.setCorePoolSize(corePoolSize);
        /**
         * 线程池能容纳同时执行的最大线程数
         */
        poolTaskExecutor.setMaxPoolSize(maxPoolSize);
        /**
         * 多余的空闲线程的最大存活时间
         */
        poolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        /**
         * 任务缓存队列的大小
         */
        poolTaskExecutor.setQueueCapacity(queueCapacity);
        /**
         * 生成线程池中线程的工厂
         * （一般默认，本次设置的目的在于给线程起名字，方便以后根据线程名定位业务）
         */
        poolTaskExecutor.setThreadFactory(threadFactory);
        /**
         * 设置拒绝策略
         */
        poolTaskExecutor.setRejectedExecutionHandler(
                (Runnable r,ThreadPoolExecutor executor) -> {
            log.warn("当前任务线程队列已满");
        });
        poolTaskExecutor.initialize();
        return poolTaskExecutor;
    }

    /**
     * 处理未知异常用
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (Throwable var1, Method var2, Object... var3) -> {
            log.error("线程池任务执行时出现未知异常");
            var1.printStackTrace();
        };
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public void setKeepAliveSeconds(Integer keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public void setQueueCapacity(Integer queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

}
