package com.redis;

import com.util.SpringUtil;
import redis.clients.jedis.JedisPool;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/16 14:13
 * @Description:
 */
public class BaseRedis {
    public static final JedisPool JEDIS_POOL = (JedisPool) SpringUtil.getBean("jedisPool");



}
