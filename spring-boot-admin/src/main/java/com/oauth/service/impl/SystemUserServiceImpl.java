package com.oauth.service.impl;

import com.common.constants.ConstantsRedisKey;
import com.common.model.JsonResult;
import com.common.utils.PasswordHelper;
import com.db.Criteria;
import com.hothub.model.TopHot;
import com.movie.model.Movie;
import com.movie.model.PersonInfo;
import com.oauth.dao.SystemUserDao;
import com.oauth.model.SystemUser;
import com.oauth.service.SystemUserService;
import com.redis.BaseRedis;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/17 17:01
 * @Description:
 */
@Service("systemUserService")
public class SystemUserServiceImpl implements SystemUserService {

    @Resource
    private SystemUserDao systemUserDao;

    @Override
    public SystemUser findByUserName(String username) {
        try {
            Criteria<SystemUser,Integer> criteria = new Criteria<>(SystemUser.class);
            criteria.addFilter("username=",username);
            return criteria.findByExample();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void initRedis() {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()){
            Criteria<PersonInfo,Integer> criteria = new Criteria<>(PersonInfo.class);
            int person = criteria.findAll().size();

            Criteria<Movie,Integer> moviec = new Criteria<>(Movie.class);
            int movie = moviec.findAll().size();


            Criteria<TopHot,Long> criteriahot = new Criteria<>(TopHot.class);
            int hot = criteriahot.findAll().size();


            jedis.hset(ConstantsRedisKey.DESKTOP,ConstantsRedisKey.DESKTOP_HOT_NUMBER,String.valueOf(hot));
            jedis.hset(ConstantsRedisKey.DESKTOP,ConstantsRedisKey.DESKTOP_PERSON_NUMBER,String.valueOf(person));
            jedis.hset(ConstantsRedisKey.DESKTOP,ConstantsRedisKey.DESKTOP_MOVIE_NUMBER,String.valueOf(movie));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public JsonResult setPassword(String oldPwd, String newPwd, String confirmPwd) {
        try {
            SystemUser userInfo =  (SystemUser)SecurityUtils.getSubject().getSession().getAttribute(ConstantsRedisKey.SESSION_USER_INFO);
            PasswordHelper passwordHelper = new PasswordHelper();
            String s = passwordHelper.encryString(oldPwd, userInfo.getSalt());
            if(!userInfo.getPassword().equals(s)){
                return new JsonResult().setMsg("旧密码不正确");
            }
            String ss = passwordHelper.encryString(newPwd, userInfo.getSalt());
            if(userInfo.getPassword().equals(ss)){
                return new JsonResult().setMsg("新密码和旧密码一致");
            }

            Criteria<SystemUser,Integer> criteria = new Criteria<>(SystemUser.class);
            userInfo.setPassword(ss);
            criteria.update(userInfo);
            return new JsonResult().setMsg("success");
        } catch (Exception e){
            e.printStackTrace();
        }
        return new JsonResult().setMsg("error");
    }
}
