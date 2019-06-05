package com.alon.common.tesk;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 * @ClassName TestTasks
 * @Description TODO
 * @Author zoujiulong
 * @Date 2019/6/4 16:55
 * @Version 1.0
 **/
@Component
public class TestTasks implements SchedulingConfigurer {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


//    @Async //异步任务
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> System.out.println("执行动态定时任务: " + LocalDateTime.now().toLocalTime()),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = "0/5 * * * * *";
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron)) {
                        // Omitted Code ..
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }
}
