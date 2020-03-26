package com.movie.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.constants.ConstantsRedisKey;
import com.common.utils.HttpRequest;
import com.exception.CusException;
import com.hothub.enums.HotType;
import com.hothub.model.TopHot;
import com.hothub.service.impl.TopHotServiceImpl;
import com.movie.model.PornHub;
import com.movie.service.PornHubService;
import com.db.Criteria;
import javax.annotation.Resource;

import com.movie.vo.SourceJson;
import com.redis.BaseRedis;
import com.util.properties.PropertiesUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.common.model.PageResult;
import com.util.PageFactory;
import com.github.pagehelper.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> PornHubService </p>
 * @author:  jidn
 * @Date :   2019-12-30 17:05:17
 */
@Service("pornHubService")
public class PornHubServiceImpl implements PornHubService{

    private Logger logger = LoggerFactory.getLogger(PornHubServiceImpl.class);


    @Override
    public PageResult findPageBySql(Map<String,String> param) {
        Map<String,Object> paraMap = new HashMap<>(3);
        Criteria<PornHub,Integer> criteria = new Criteria<>(PornHub.class);
        Page<PornHub> page = PageFactory.getPage(param);
        criteria.findAll();
        return new PageResult(page,page.getTotal(), page.getPages(), page.getPageSize());
    }

    @Transactional(rollbackFor = {RuntimeException.class, CusException.class})
    @Override
    public void crawlerPorn() {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()){
            Criteria<PornHub,Long> criteria = new Criteria<>(PornHub.class);

            String s = jedis.get(ConstantsRedisKey.CRAW_URL);
            if(!StringUtils.isEmpty(s)){
                SourceJson json = JSONObject.parseObject(s, SourceJson.class);
                for(int i = 1; i < 500; i++){
                    Document document = Jsoup.connect(HttpRequest.buildUrl(json.getHttpApi(),i))
                            .timeout(10000)
                            .ignoreContentType(true)
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                            .get();

                   // Element element1 = document.selectFirst("list");
                    // System.out.println(element1.attr("pagecount")+"==="+element1.attr("pagesize"));
                    Elements element = document.select("video");
                    element.forEach(em->{
                        PornHub porn = new PornHub();
                        String name = em.select("name").text();
                        String picture = em.select("pic").text();
                        String m3u8 = em.select("dl").select("dd").text();
                        String last = em.select("last").text();
                        String type = em.select("type").text();
                        porn.setName(name);
                        porn.setPicture(picture);
                        porn.setM3u8(m3u8);
                        porn.setLast(last);
                        porn.setType(type);
                        criteria.save(porn);
                    });
                }
            }
            logger.info("==========Quartz Job 【crawlerPorn】 Execute Completed===");
        } catch (Exception e){
            throw new CusException("爬取Porn数据异常",e);
        }
    }

    @Override
    public void crawlerPython() {
        try {
            String osName = System.getProperty("os.name");
            System.out.println("---------------------------------" + osName + "---------------------------------");
            if(osName.toLowerCase().indexOf("win") != -1){
                System.out.println();
                String path = this.getClass().getClassLoader().getResource("").getPath();
                String filePath = path + "/py/crawlerPorn.exe";
                System.out.println("--------------------------------- win ---------------------------------");
                String command = filePath.substring(1, filePath.length());

                System.out.println("------------------------ 执行命令：" + command + " ------------------------");

                Runtime rt = Runtime.getRuntime();
                Process p = rt.exec(command);

            }else if(osName.toLowerCase().indexOf("linux") != -1){
                String path = this.getClass().getClassLoader().getResource("").getPath();
                String filePath = path + "/py/crawlerPorn.py";
                System.out.println("--------------------------------- linux ---------------------------------");
                String command = "." + filePath  ;

                System.out.println("------------------------ 执行命令：" + command + " ------------------------");

                //exec1("chmod +x " + filePath); // chmod 777
                Runtime rt = Runtime.getRuntime();
                Process p = rt.exec(new String[]{"/bin/sh", "-c", "cd /; chmod +x "+filePath+";" + command});

            }else if(osName.toLowerCase().indexOf("mac") != -1){

            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
