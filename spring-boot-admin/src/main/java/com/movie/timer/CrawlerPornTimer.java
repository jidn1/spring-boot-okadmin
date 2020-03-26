package com.movie.timer;

import com.hothub.timer.CrawlerTimer;
import com.quartz.QuartzJob;
import com.quartz.QuartzManager;
import com.quartz.ScheduleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/30 19:46
 * @Description:
 */
public class CrawlerPornTimer {

    private static Logger logger = LoggerFactory.getLogger(CrawlerTimer.class);

    public static void initPornTimer(){
        QuartzManager.removeJob("crawlerPornJob");
        ScheduleJob crawlerPornJob = new ScheduleJob();
        crawlerPornJob.setSpringId("pornHubService");
        crawlerPornJob.setMethodName("crawlerPorn");
        QuartzManager.addJob("crawlerPorn", crawlerPornJob, QuartzJob.class, "10 0/5 * * * ?");
        logger.info("================crawler Porn Job start");
    }
}
