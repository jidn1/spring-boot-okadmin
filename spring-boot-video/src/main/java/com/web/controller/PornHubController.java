package com.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.aop.ServiceLimit;
import com.common.constants.ConstantsRedisKey;
import com.common.model.JsonResult;
import com.common.model.PageResult;
import com.common.utils.HttpServletRequestUtils;
import com.redis.BaseRedis;
import com.web.service.PornHubService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2020/1/2 19:11
 * @Description:
 */
@RestController
@RequestMapping("/pornHub")
public class PornHubController {

    @Resource
    private PornHubService pornHubService;

    @ServiceLimit(limitType= ServiceLimit.LimitType.IP)
    @RequestMapping("/list")
    @ResponseBody
    public PageResult list(HttpServletRequest request) {
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        return pornHubService.findPageBySql(params);
    }


    @ServiceLimit(limitType= ServiceLimit.LimitType.IP)
    @RequestMapping("/type")
    @ResponseBody
    public JsonResult type(HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()){
            String s = jedis.get(ConstantsRedisKey.VIDEO_PORN_TYPE);
            List<String> list = JSONObject.parseArray(s, String.class);
            System.out.println(""+s);
            jsonResult.setSuccess(true);
            jsonResult.setObj(JSON.toJSONString(s));
        } catch (Exception e) {

        }
        return jsonResult;
    }
}
