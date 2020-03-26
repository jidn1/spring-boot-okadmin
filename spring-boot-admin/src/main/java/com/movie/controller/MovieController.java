package com.movie.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.annotation.CommonLog;
import com.common.constants.ConstantsRedisKey;
import com.common.model.JsonResult;
import com.common.model.PageResult;
import com.db.Criteria;
import com.redis.BaseRedis;
import com.thread.ThreadPool;
import com.util.HttpServletRequestUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import com.movie.model.Movie;
import com.movie.service.MovieService;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;


/**
 * @Copyright: 正经吉
 * @author: jidn
 * @version: V1.0
 * @Date: 2019-12-20 16:49:50
 */
@Api(value = "登录-首页")
@CrossOrigin
@RestController
@RequestMapping("/movie")
public class MovieController {

    @Resource
    private MovieService movieService;

    @RequestMapping("/list")
    @ResponseBody
    public PageResult list(HttpServletRequest request) {
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        return movieService.findPageBySql(params);
    }

    @CommonLog(name = "添加电影")
    @RequestMapping("/save")
    @ResponseBody
    public JsonResult save(Movie movie) {
        Criteria<Movie, Integer> criteria = new Criteria<>(Movie.class);
        criteria.save(movie);
        return new JsonResult().setSuccess(true).setMsg("success");
    }

    @RequestMapping("/modify")
    @ResponseBody
    public JsonResult modify(HttpServletRequest request, Movie movie) {
        Criteria<Movie, Integer> criteria = new Criteria<>(Movie.class);
        criteria.update(movie);
        return new JsonResult().setSuccess(true).setMsg("success");
    }


    @RequestMapping("/remove")
    @ResponseBody
    public JsonResult remove(String idsStr) {
        Criteria<Movie, Integer> criteria = new Criteria<>(Movie.class);
        String[] idss = idsStr.split(",");
        for (String id : idss) {
            criteria.addFilter("id=", id);
            Movie movie = criteria.findByExample();
            movie.setIsdelete(1);
            criteria.update(movie);
        }
        return new JsonResult().setSuccess(true).setMsg("success");
    }


    @RequestMapping("/play")
    @ResponseBody
    public JsonResult play(String idsStr) {
        Criteria<Movie, Integer> criteria = new Criteria<>(Movie.class);
        String[] idss = idsStr.split(",");
        Movie movie = null;
        for (String id : idss) {
            criteria.addFilter("id=", id);
            movie = criteria.findByExample();
        }
        return new JsonResult().setSuccess(true).setMsg("success").setObj(movie);
    }

    @RequestMapping("/refreshRedis")
    @ResponseBody
    public JsonResult refreshRedis() {
        Criteria<Movie, Integer> criteria = new Criteria<>(Movie.class);
        ThreadPool.exe(new Runnable() {
            @Override
            public void run() {
                try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()){
                    jedis.del(ConstantsRedisKey.VIDEO_MOVIE_LIST);
                    List<Movie> movieList = criteria.findAll();
                    for (Movie m : movieList) {
                        jedis.rpush(ConstantsRedisKey.VIDEO_MOVIE_LIST, JSONObject.toJSONString(m));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return new JsonResult().setSuccess(true).setMsg("success");
    }


    @CommonLog(name = "手动爬取资源")
    @RequestMapping("/manualCraw")
    @ResponseBody
    public JsonResult manualCraw(String idsStr) {
        ThreadPool.exe(new Runnable() {
            @Override
            public void run() {
                try {
                    movieService.crawlerPython();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return new JsonResult().setSuccess(true).setMsg("爬取中,请稍后,大概需要几分钟");
    }
}
