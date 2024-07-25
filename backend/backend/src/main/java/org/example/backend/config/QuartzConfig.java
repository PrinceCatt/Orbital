package org.example.backend.config;

import org.example.backend.utils.LikeTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.quartz.*;

@Configuration
public class QuartzConfig {
    private static final String LikeTaskIdentity = "LikeTaskQuartz";

    @Bean
    public JobDetail quartzDetail() {
        return JobBuilder.newJob(LikeTask.class).withIdentity(LikeTaskIdentity).storeDurably().build();
    }

    @Bean
    public Trigger quartzTrigger() {
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().
                //the following line is to set the time interval for updating, which is per 60 seconds for now
                withIntervalInSeconds(60).repeatForever();
        return TriggerBuilder.newTrigger().forJob(quartzDetail()).
                                           withIdentity(LikeTaskIdentity).
                                           withSchedule(simpleScheduleBuilder).
                                           build();
    }
}
