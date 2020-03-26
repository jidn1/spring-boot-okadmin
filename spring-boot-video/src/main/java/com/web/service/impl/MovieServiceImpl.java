package com.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.common.constants.ConstantsRedisKey;
import com.common.utils.Page;
import com.redis.BaseRedis;
import com.web.model.Movie;
import com.web.service.MovieService;
import com.db.Criteria;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.common.model.PageResult;
import com.util.PageFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> MovieService </p>
 *
 * @author: jidn
 * @Date :   2020-01-02 13:40:48
 */
@Service("movieService")
public class MovieServiceImpl implements MovieService {


    @Override
    public PageResult findPageBySql(Map<String, String> param) {
        List<Movie> list = new ArrayList<>();
        Page page = new Page(param);
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            Long total = jedis.llen(ConstantsRedisKey.VIDEO_MOVIE_LIST);
            page.setTotal(total);

            List<String> lrange = jedis.lrange(ConstantsRedisKey.VIDEO_MOVIE_LIST, page.getPages(), page.getPageSize());
            for (String l : lrange) {
                Movie movie = JSONObject.parseObject(l, Movie.class);
                list.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PageResult(list, page.getTotal(), page.getPages(), page.getPageSize());
    }

}
