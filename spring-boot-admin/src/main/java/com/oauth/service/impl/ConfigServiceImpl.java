package com.oauth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.common.cache.OkAdminCache;
import com.common.constants.ConstantsRedisKey;
import com.oauth.enums.ConfigTypeKey;
import com.oauth.model.Config;
import com.oauth.service.ConfigService;
import com.db.Criteria;

import javax.annotation.Resource;

import com.redis.BaseRedis;
import com.util.oss.OssUtil;
import org.springframework.stereotype.Service;
import com.common.model.PageResult;
import com.util.PageFactory;
import com.github.pagehelper.Page;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> ConfigService </p>
 *
 * @author: jidn
 * @Date :   2019-12-27 18:14:58
 */
@Service("configService")
public class ConfigServiceImpl implements ConfigService {


    @Override
    public PageResult findPageBySql(Map<String, String> param) {
        Map<String, Object> paraMap = new HashMap<>(3);
        Criteria<Config, Integer> criteria = new Criteria<>(Config.class);
        Page<Config> page = PageFactory.getPage(param);
        criteria.findAll();
        return new PageResult(page, page.getTotal(), page.getPages(), page.getPageSize());
    }

    @Override
    public void initRedisConfig() {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            Criteria<Config, Integer> criteria = new Criteria<>(Config.class);
            List<Config> list = criteria.findAll();

            List<Config> pc = new ArrayList<>();
            List<Config> video = new ArrayList<>();
            for (Config c : list) {
                switch (c.getDatatype()) {
                    case 1:
                        video.add(c);
                        break;
                    case 2:
                        pc.add(c);
//                        if (c.getConfigkey().equals(ConfigTypeKey.TYPE_OK_HEAD.getKey())) {
//                            String url = OssUtil.getUrl(c.getValue(), false);
//                            OkAdminCache.cache_language.put(c.getConfigkey(),url);
//                        } else {
//
//                        }
                        OkAdminCache.cache_language.put(c.getConfigkey(),c.getValue());
                        break;
                    default:
                        pc.add(c);
                        break;
                }
            }

            jedis.hset(ConstantsRedisKey.BASE_CONFIG,"pc", JSONObject.toJSONString(pc));
            jedis.hset(ConstantsRedisKey.BASE_CONFIG,"video", JSONObject.toJSONString(video));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
