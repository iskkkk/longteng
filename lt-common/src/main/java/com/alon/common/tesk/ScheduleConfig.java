package com.alon.common.tesk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @ClassName ScheduleConfig
 * @Description TODO
 * @Author zoujiulong
 * @Date 2019/6/4 18:45
 * @Version 1.0
 **/
@Configuration
public class ScheduleConfig {
    /**
      * 方法表述: 解决定时任务和websocket冲突
      * @Author zoujiulong
      * @Date 13:40 2019/6/5
      * @param
      * @return org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
    */
    @Bean
    public ThreadPoolTaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.initialize();
        return taskScheduler;
    }
}
