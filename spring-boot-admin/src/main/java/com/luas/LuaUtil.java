package com.luas;

import com.alibaba.fastjson.JSONObject;
import com.redis.BaseRedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author <a href="mailto:HelloHeSir@gmail.com">Mr_He</a>
 * @Copyright (c)</   b> HeC<br/>
 * @createTime 2018/7/23 10:27
 * @Description: lua工具类
 */
public class LuaUtil {

    public static final Logger log = LoggerFactory.getLogger(LuaUtil.class);


    /**
     * 调用lua 无返回值
     */
    public static boolean setCall(ClassPathResource resource, List<String> keys, List<String> argv) {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            DefaultRedisScript<Long> script = new DefaultRedisScript<>();
            script.setScriptSource(new ResourceScriptSource(resource));
            script.setResultType(Long.class);
            Object eval = jedis.eval(script.getScriptAsString(), keys, argv);
            System.out.println(JSONObject.toJSONString(eval));
        } catch (Exception e) {
            log.error("lua解析失败", e);
        }
        return false;
    }

    /**
     * 有返回值
     */
    public static List<String> getCall(ClassPathResource resource) {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            DefaultRedisScript<List> script = new DefaultRedisScript<>();
            script.setScriptSource(new ResourceScriptSource(resource));
            script.setResultType(List.class);
            return (List<String>) jedis.eval(script.getScriptAsString());
        } catch (Exception e) {
            log.error("lua解析失败", e);
        }
        return null;
    }


}