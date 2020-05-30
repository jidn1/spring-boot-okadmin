package com.hothub.timer;

import com.alibaba.fastjson.JSONObject;
import com.common.utils.HttpRequest;
import com.hothub.model.TopHot;
import com.quartz.QuartzManager;
import com.quartz.ScheduleJob;
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

//            String requestData = HttpRequest.doGet("http://zcyydy.com/api?ac=videolist&pg=1&t=all");
//            JSONObject jsonObject = JSONObject.parseObject(requestData);
//            String data = jsonObject.getString("data");
//            JSONArray array = JSONObject.parseArray(data);
//            for(int i =0 ; i < array.size() ;i++) {
//                JSONObject json = array.getJSONObject(i);
//                String target = json.getString("target");
//                JSONObject targetData = JSONObject.parseObject(target);
//
//                TopHot hot = new TopHot();
//                String href = targetData.getString("url").replace("api", "www").replace("questions", "question");
//                String title = targetData.getString("title");
//                hot.setTitle(title);
//                hot.setUrl(href);
//                hot.setOriginalUrl(href);
//                hot.setType(HotType.TYPE3.getCode());
//            }


            String requestData = HttpRequest.doGet("https://www.ithome.com/news/getnews");
            JSONObject jsonObject = JSONObject.parseObject(requestData);
            System.out.println("newId==="+jsonObject.getString("newsid"));
            System.out.println("news==="+jsonObject.getString("news"));
            String html = jsonObject.getString("news");
            Document document = Jsoup.parse(html);
            Elements element = document.selectFirst("div").select("li");
            element.forEach(em->{
                TopHot hot = new TopHot();
                String href = em.select("a").attr("href");
                String title = em.select("a").text();
                System.out.println(href+"===   "+title);

            });


        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
