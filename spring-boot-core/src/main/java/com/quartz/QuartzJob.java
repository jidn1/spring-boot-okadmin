package com.quartz;

import com.util.SpringUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.lang.reflect.Method;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/17 14:40
 * @Description:
 *
 *         ScheduleJob job = new ScheduleJob();
 *         job.setJobName("任务02");
 *         job.setCronExpression("0/2 * * * * ?");
 *         job.setBeanName("testJob02");
 *         job.setMethodName("execute");
 *
 */

public class QuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取调度数据
        ScheduleJob scheduleJob = (ScheduleJob) jobExecutionContext.getMergedJobDataMap().get("scheduleJob");

        //获取对应的Bean
        Object object = SpringUtil.getBean(scheduleJob.getBeanName());
        try {
            //利用反射执行对应方法
            Method method = object.getClass().getMethod(scheduleJob.getMethodName());
            method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
