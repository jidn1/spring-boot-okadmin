package com.hothub.timer;

import com.alibaba.fastjson.JSONObject;
import com.common.utils.HttpRequest;
import com.hothub.enums.HotType;
import com.hothub.model.TopHot;
import com.quartz.QuartzManager;
import com.quartz.ScheduleJob;
import com.util.properties.PropertiesUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/26 17:33
 * @Description: 定时器时间岔开  因为操作同一张表 时间一样容易锁表
 */
public class CrawlerTimer {

    private static Logger logger = LoggerFactory.getLogger(CrawlerTimer.class);


    public static void initCnBlogTimer(){
        QuartzManager.removeJob("initCnBlogJob");
        ScheduleJob initCnBlogJob = new ScheduleJob();
        initCnBlogJob.setJobName("initCnBlogJob");
        initCnBlogJob.setBeanName("topHotService");
        initCnBlogJob.setMethodName("crawlerCnBlog");
        initCnBlogJob.setCronExpression("5 0/5 * * * ?");
        QuartzManager.addJob(initCnBlogJob);
        logger.info("================crawler CnBlog Job start");
    }

    public static void initBaiDuTimer(){
        QuartzManager.removeJob("initBaiDuJob");
        ScheduleJob initBaiDuJob = new ScheduleJob();
        initBaiDuJob.setJobName("initBaiDuJob");
        initBaiDuJob.setBeanName("topHotService");
        initBaiDuJob.setMethodName("crawlerBaiDu");
        initBaiDuJob.setCronExpression("10 0/5 * * * ?");
        QuartzManager.addJob(initBaiDuJob);
        logger.info("================crawler BaiDu Job start");
    }

    public static void initZhiHuTimer(){
        QuartzManager.removeJob("initZhiHuJob");
        ScheduleJob initZhiHuJob = new ScheduleJob();
        initZhiHuJob.setJobName("initZhiHuJob");
        initZhiHuJob.setBeanName("topHotService");
        initZhiHuJob.setMethodName("crawlerZhiHu");
        initZhiHuJob.setCronExpression("15 0/5 * * * ?");
        QuartzManager.addJob(initZhiHuJob);
        logger.info("================crawler ZhiHu Job start");
    }

    public static void initBbsTimer(){
        QuartzManager.removeJob("initBbsJob");
        ScheduleJob initBbsJob = new ScheduleJob();
        initBbsJob.setJobName("initBbsJob");
        initBbsJob.setBeanName("topHotService");
        initBbsJob.setMethodName("crawlerBbs");
        initBbsJob.setCronExpression("20 0/5 * * * ?");
        QuartzManager.addJob(initBbsJob);
        logger.info("================crawler Bbs Job start");
    }


    public static void main(String[] args) {
        try {

            Document document = Jsoup.connect(PropertiesUtils.APP.getProperty("CRAWLER_CN_BLOG"))
                    .timeout(10000)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                    .get();

            Elements element = document.select(".item-list").eq(1).select("li");
            element.forEach(em->{
                String href = em.select("a").attr("href");
                String title = em.select("a").text().replace("search","");
                System.out.println("title==="+title);
            });




        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
