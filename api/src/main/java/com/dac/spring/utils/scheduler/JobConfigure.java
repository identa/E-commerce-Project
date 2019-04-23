//package com.dac.spring.utils.scheduler;
//
//import org.quartz.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class JobConfigure {
//    @Bean
//    public JobDetail campaignJobDetails() {
//        return JobBuilder.newJob(CampaignJob.class).withIdentity("campaignJob")
//                .storeDurably().build();
//    }
//
//    @Bean
//    public Trigger jobATrigger(JobDetail jobADetails) {
//
//        return TriggerBuilder.newTrigger().forJob(jobADetails)
//
//                .withIdentity("campaignJob")
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * ? * * *"))
//                .build();
//    }
//}
