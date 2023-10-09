package com.xirui.service;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.Executor;

@Component
@EnableScheduling
public class Service1 {
    // 每隔5秒执行一次任务
    @Async
//    @Scheduled(fixedRate = 1000)
    public void task1() throws InterruptedException {
        System.err.println(Thread.currentThread().getName() + "--任务1：每隔5秒执行一次 ：" + new Date());
        Thread.sleep(2000);
    }

    //    @Async
//    @Scheduled(fixedRate = 1000)
    public void task3() throws InterruptedException {
        System.err.println(Thread.currentThread().getId() + "--任务3：每隔5秒执行一次 ：" + new Date());
        Thread.sleep(2000);
    }

    // 每天上午10点执行任务
//    @Scheduled(cron = "0 0 10 * * ?")
    public void task2() {
        System.err.println("任务2：每天上午10点执行");
    }

//        @Bean
//    public ThreadPoolTaskScheduler taskScheduler() {
//        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
//        taskScheduler.setPoolSize(5); // 设置线程池大小
//        taskScheduler.setThreadNamePrefix("my-scheduled-task-");
//        taskScheduler.setAwaitTerminationSeconds(30);
//        return taskScheduler;
//    }

//    @Bean(name = "new_task")
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(10);
        //最大线程数
        executor.setMaxPoolSize(20);
        //队列容量
        executor.setQueueCapacity(200);
        //活跃时间
        executor.setKeepAliveSeconds(60);
        //线程名字前缀
        executor.setThreadNamePrefix("task-");

        executor.setAllowCoreThreadTimeOut(true);
        executor.setAwaitTerminationSeconds(30);
        return executor;
    }
}
