package com.quartz;

import spring.org.quartz.*;
import spring.org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/17 14:42
 * @Description:
 */
@Configuration
public class QuartzManager {

    private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();


    public static void addJob(ScheduleJob job) {
        try {
            Scheduler scheduler = gSchedulerFactory.getScheduler();

            //创建触发器
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName())
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))
                    .startNow()
                    .build();

            //创建任务
            JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class)
                    .withIdentity(job.getJobName())
                    .build();

            //传入调度的数据，在QuartzFactory中需要使用
            jobDetail.getJobDataMap().put("scheduleJob", job);

            //调度作业
            scheduler.scheduleJob(jobDetail, trigger);

            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





    /**
     * 移除一个任务
     *
     * @param jobName
     */
    public static void removeJob(String jobName) {
        try {
            Scheduler scheduler = gSchedulerFactory.getScheduler();
            JobKey jobKey = new JobKey(jobName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
                scheduler.deleteJob(jobKey);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 启动所有定时任务
     */
    public static void startJobs() {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭所有定时任务
     */
    public static void shutdownJobs() {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
