package com.hothub.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.annotation.CommonLog;
import com.thread.ThreadPool;
import com.util.HttpServletRequestUtils;
import com.common.model.JsonResult;
import com.common.model.PageResult;
import io.swagger.annotations.Api;
import com.db.Criteria;
import org.springframework.web.bind.annotation.*;
import com.hothub.model.TopHot;
import com.hothub.service.TopHotService;

import java.util.Map;

/**
 * @Copyright: 正经吉
 * @author: jidn
 * @version: V1.0
 * @Date: 2019-12-26 17:33:13
 */
@Api(value = "")
@RestController
@RequestMapping("/topHot")
public class TopHotController {

    @Resource
    private TopHotService topHotService;

    @RequestMapping("/list")
    @ResponseBody
    public PageResult list(HttpServletRequest request) {
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        return topHotService.findPageBySql(params);
    }


    @RequestMapping("/save")
    @ResponseBody
    public JsonResult save(TopHot topHot) {
        Criteria<TopHot, Integer> criteria = new Criteria<>(TopHot.class);
        criteria.save(topHot);
        return new JsonResult().setSuccess(true).setMsg("success");
    }

    @RequestMapping("/modify")
    @ResponseBody
    public JsonResult modify(HttpServletRequest request, TopHot topHot) {
        Criteria<TopHot, Integer> criteria = new Criteria<>(TopHot.class);
        criteria.update(topHot);
        return new JsonResult().setSuccess(true).setMsg("success");
    }


    @RequestMapping("/remove")
    @ResponseBody
    public JsonResult remove(String idsStr) {
        Criteria<TopHot, Integer> criteria = new Criteria<>(TopHot.class);
        String[] idss = idsStr.split(",");
        for (String id : idss) {
            criteria.deleteByPrimary(Integer.valueOf(id));
        }
        return new JsonResult().setSuccess(true).setMsg("success");
    }

    @CommonLog(name = "手动爬取热搜资源")
    @RequestMapping("/manualCraw")
    @ResponseBody
    public JsonResult manualCraw(String idsStr) {
        ThreadPool.exe(new Runnable() {
            @Override
            public void run() {
                try {
                    topHotService.crawlerCnBlog();
                    Thread.sleep(3000);
                    topHotService.crawlerBaiDu();
                    Thread.sleep(3000);
                    topHotService.crawlerBbs();
                    Thread.sleep(3000);
                    topHotService.crawlerZhiHu();
                    Thread.sleep(3000);
                    topHotService.crawlerItHome();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return new JsonResult().setSuccess(true).setMsg("success");
    }

}
