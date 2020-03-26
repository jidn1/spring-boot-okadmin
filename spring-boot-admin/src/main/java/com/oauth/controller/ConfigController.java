package com.oauth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.internal.OSSUtils;
import com.common.utils.JsoupUtil;
import com.oauth.enums.ConfigTypeKey;
import com.util.HttpServletRequestUtils;
import com.common.model.JsonResult;
import com.common.model.PageResult;
import com.util.oss.OssUtil;
import io.swagger.annotations.Api;
import com.db.Criteria;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.oauth.model.Config;
import com.oauth.service.ConfigService;

import java.util.*;

/**
 * @Copyright: 正经吉
 * @author: jidn
 * @version: V1.0
 * @Date: 2019-12-27 18:14:58
 */
@Api(value = "")
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Resource
    private ConfigService configService;

    @RequestMapping("/list")
    @ResponseBody
    public PageResult list(HttpServletRequest request) {
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        return configService.findPageBySql(params);
    }


    @RequestMapping("/save")
    @ResponseBody
    public JsonResult save(Config config) {
        Criteria<Config, Integer> criteria = new Criteria<>(Config.class);
        criteria.save(config);
        return new JsonResult().setSuccess(true).setMsg("success");
    }

    @RequestMapping("/modify")
    @ResponseBody
    public JsonResult modify(HttpServletRequest request, Config config) {
        Criteria<Config, Integer> criteria = new Criteria<>(Config.class);
        criteria.update(config);
        return new JsonResult().setSuccess(true).setMsg("success");
    }


    @RequestMapping("/remove")
    @ResponseBody
    public JsonResult remove(String idsStr) {
        Criteria<Config, Integer> criteria = new Criteria<>(Config.class);
        String[] idss = idsStr.split(",");
        for (String id : idss) {
            criteria.deleteByPrimary(Integer.valueOf(id));
        }
        return new JsonResult().setSuccess(true).setMsg("success");
    }


    @RequestMapping("/baseConfig")
    @ResponseBody
    public JsonResult baseConfig(HttpServletRequest request) {
        Criteria<Config, Integer> criteria = new Criteria<>(Config.class);
        criteria.addFilter("typekey=", ConfigTypeKey.TYPE_OK_ADMIN.getKey());
        List<Config> listByExample = criteria.findListByExample();

        Map<String, String> map = new HashMap(listByExample.size());
        for (Config c : listByExample) {
//            if (c.getConfigkey().equals(ConfigTypeKey.TYPE_OK_HEAD.getKey())) {
//                String url = OssUtil.getUrl(c.getValue(), false);
//                map.put(c.getConfigkey(), url);
//            } else {
//
//            }
            map.put(c.getConfigkey(), c.getValue());
        }

        return new JsonResult().setSuccess(true).setMsg("success").setObj(JSONObject.toJSONString(map));
    }


    @RequestMapping("/baseConfigModify")
    @ResponseBody
    public JsonResult baseConfigModify(HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        Criteria<Config, Integer> criteria = new Criteria<>(Config.class);
        try {
            String typeKey = request.getParameter("typeKey");
            Enumeration<String> names = request.getParameterNames();
            criteria.addFilter("typekey=", typeKey);
            List<Config> list = criteria.findListByExample();

            while (names.hasMoreElements()) {
                String name = names.nextElement();
                String value = StringUtils.trimAllWhitespace(request.getParameter(name));
                for (Config config : list) {
                    if (!StringUtils.isEmpty(value) && config.getConfigkey().equals(name)) {
                        config.setValue(JsoupUtil.clean(value));
                        criteria.update(config);
                    }
                }
            }
            jsonResult.setSuccess(true);
            jsonResult.setMsg("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

}
