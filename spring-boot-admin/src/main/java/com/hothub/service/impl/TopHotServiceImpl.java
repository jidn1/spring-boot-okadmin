package com.hothub.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.utils.HttpRequest;
import com.exception.CusException;
import com.hothub.enums.HotType;
import com.hothub.model.TopHot;
import com.hothub.service.TopHotService;
import com.db.Criteria;

import com.util.properties.PropertiesUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.common.model.PageResult;
import com.util.PageFactory;
import com.github.pagehelper.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> TopHotService </p>
 * @author:  jidn
 * @Date :   2019-12-26 17:33:13
 */
@Service("topHotService")
public class TopHotServiceImpl implements TopHotService{
    private Logger logger = LoggerFactory.getLogger(TopHotServiceImpl.class);

    @Override
    public PageResult findPageBySql(Map<String,String> param) {
        Map<String,Object> paraMap = new HashMap<>(3);
        Criteria<TopHot,Long> criteria = new Criteria<>(TopHot.class);
        Page<TopHot> page = PageFactory.getPage(param);

        String title = param.get("title");
        String type = param.get("type");

        if(!StringUtils.isEmpty(title)){
            criteria.addFilter("title_like","%"+title+"%");
        }
        if(!StringUtils.isEmpty(type)){
            criteria.addFilter("type=",type);
        }

        criteria.addFilter("1=","1");

        criteria.findListByExample();
        return new PageResult(page,page.getTotal(), page.getPages(), page.getPageSize());
    }

    @Transactional(rollbackFor = {RuntimeException.class, CusException.class})
    @Override
    public void crawlerCnBlog() {
        Criteria<TopHot,Long> criteria = new Criteria<>(TopHot.class);
        try {
            criteria.addFilter("type=",HotType.TYPE1.getCode());
            criteria.deleteByExample();

            Document document = Jsoup.connect(PropertiesUtils.APP.getProperty("CRAWLER_CN_BLOG"))
                    .timeout(10000)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                    .get();

            Elements element = document.select(".item-list").eq(1).select("li");
            element.forEach(em->{
                TopHot hot = new TopHot();
                String href = em.select("a").attr("href");
                String title = em.select("a").text().replace("search","");
                hot.setTitle(title);
                hot.setUrl(href);
                hot.setOriginalUrl(href);
                hot.setType(HotType.TYPE1.getCode());
                criteria.save(hot);
            });
            logger.info("==========Quartz Job 【CrawlerCnBlog】 Execute Completed===");
        } catch (Exception e){
            throw new CusException("爬取博客园数据异常",e);
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class, CusException.class})
    @Override
    public void crawlerBaiDu() {
        try {
            Criteria<TopHot,Long> criteria = new Criteria<>(TopHot.class);
            criteria.addFilter("type=",HotType.TYPE2.getCode());
            criteria.deleteByExample();

            Document document = Jsoup.connect(PropertiesUtils.APP.getProperty("CRAWLER_BAI_DU"))
                    .timeout(10000)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                    .get();

            Elements element = document.select(".list-table").select("td").select(".keyword");
            element.forEach(em->{
                TopHot hot = new TopHot();
                String href = em.select("a").attr("href");
                String title = em.select("a").text().replace("search","");
                hot.setTitle(title);
                hot.setUrl(href);
                hot.setOriginalUrl(href);
                hot.setType(HotType.TYPE2.getCode());
                criteria.save(hot);
            });
            logger.info("==========Quartz Job 【crawlerBaiDu】 Execute Completed===");
        } catch (Exception e){
            throw new CusException("爬取百度热搜数据异常",e);
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class, CusException.class})
    @Override
    public void crawlerZhiHu() {
        try {
            Criteria<TopHot,Long> criteria = new Criteria<>(TopHot.class);
            criteria.addFilter("type=",HotType.TYPE3.getCode());
            criteria.deleteByExample();

            String requestData = HttpRequest.doGet(PropertiesUtils.APP.getProperty("CRAWLER_ZHI_HU"));
            JSONObject jsonObject = JSONObject.parseObject(requestData);
            String data = jsonObject.getString("data");
            JSONArray array = JSONObject.parseArray(data);
            for(int i =0 ; i < array.size() ;i++){
                JSONObject json = array.getJSONObject(i);
                String target = json.getString("target");
                JSONObject targetData = JSONObject.parseObject(target);

                TopHot hot = new TopHot();
                String href = targetData.getString("url").replace("api","www").replace("questions","question");
                String title = targetData.getString("title");
                hot.setTitle(title);
                hot.setUrl(href);
                hot.setOriginalUrl(href);
                hot.setType(HotType.TYPE3.getCode());
                criteria.save(hot);
            }
            logger.info("==========Quartz Job 【crawlerZhiHu】 Execute Completed===");
        } catch (Exception e){
           throw new CusException("爬取知乎数据异常",e);
        }
    }


    @Transactional(rollbackFor = {RuntimeException.class, CusException.class})
    @Override
    public void crawlerBbs() {
        try {
            Criteria<TopHot,Long> criteria = new Criteria<>(TopHot.class);
            criteria.addFilter("type=",HotType.TYPE4.getCode());
            criteria.deleteByExample();

            Document document = Jsoup.connect(PropertiesUtils.APP.getProperty("CRAWLER_HU_PU"))
                    .timeout(10000)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                    .get();

            Elements element = document.select(".bbsHotPit").select(".list").eq(0).select("li");
            element.forEach(em->{
                TopHot hot = new TopHot();
                String href = em.select("a").attr("href");
                String title = em.select("a").attr("title");
                hot.setTitle(title);
                hot.setUrl("https://bbs.hupu.com/"+href);
                hot.setOriginalUrl("https://bbs.hupu.com/"+href);
                hot.setType(HotType.TYPE4.getCode());
                criteria.save(hot);
            });
            logger.info("==========Quartz Job 【crawlerBbs】 Execute Completed===");
        } catch (Exception e){
            throw new CusException("爬取虎扑步行街数据异常",e);
        }
    }



    @Transactional(rollbackFor = {RuntimeException.class, CusException.class})
    @Override
    public void crawlerItHome() {
        try {
            Criteria<TopHot,Long> criteria = new Criteria<>(TopHot.class);
            criteria.addFilter("type=",HotType.TYPE5.getCode());
            criteria.deleteByExample();

            String requestData = HttpRequest.doGet(PropertiesUtils.APP.getProperty("CRAWLER_IT_HOME"));
            JSONObject jsonObject = JSONObject.parseObject(requestData);
            String html = jsonObject.getString("news");
            Document document = Jsoup.parse(html);
            Elements element = document.selectFirst("div").select("li");
            element.forEach(em->{
                TopHot hot = new TopHot();
                String href = em.select("a").attr("href");
                String title = em.select("a").text();

                hot.setTitle(title);
                hot.setUrl(href);
                hot.setOriginalUrl(href);
                hot.setType(HotType.TYPE5.getCode());
                criteria.save(hot);
            });

            logger.info("==========Quartz Job 【crawlerItHome】 Execute Completed===");
        } catch (Exception e){
            throw new CusException("爬取it之家数据异常",e);
        }
    }
}
