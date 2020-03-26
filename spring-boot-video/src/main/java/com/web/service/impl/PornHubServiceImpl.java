package com.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.common.constants.ConstantsRedisKey;
import com.common.model.PageResult;
import com.common.utils.Page;
import com.redis.BaseRedis;
import com.web.model.Movie;
import com.web.model.PornHub;
import com.web.service.PornHubService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2020/1/2 19:12
 * @Description:
 */
@Service("pornHubService")
public class PornHubServiceImpl implements PornHubService {


    @Override
    public PageResult findPageBySql(Map<String, String> param) {
        List<PornHub> list = new ArrayList<>();
        Page page = new Page(param);
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            String type = param.get("type");
            if(StringUtils.isEmpty(type)){
                type = ConstantsRedisKey.VIDEO_PORN_LIST;
            } else {
                type = ConstantsRedisKey.VIDEO_PORN_LIST+":"+type;
            }

            Long total = jedis.llen(type);
            page.setTotal(total);
            List<String> lrange = jedis.lrange(type, page.getPages(), page.getPageSize());
            if(lrange != null && lrange.size() > 0){
                for (String l : lrange) {
                    PornHub movie = JSONObject.parseObject(l, PornHub.class);
                    list.add(movie);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PageResult(list, page.getTotal(), page.getPages(), page.getPageSize());
    }
}
