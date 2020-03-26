package com.oauth.controller;

import com.annotation.CommonLog;
import com.common.constants.ConstantsRedisKey;
import com.common.model.JsonResult;
import com.oauth.model.SystemMenu;
import com.oauth.model.SystemUser;
import com.oauth.service.SystemMenuService;
import com.oauth.service.SystemUserService;
import com.oauth.vo.MenuDataVo;
import com.redis.BaseRedis;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/26 16:11
 * @Description:
 */
@Api(value = "登录-首页")
@Controller
@RequestMapping("/system")
public class SystemUserController {
    @Resource
    private SystemUserService systemUserService;
    @Resource
    private SystemMenuService systemMenuService;


    @RequestMapping("/desktop")
    @ResponseBody
    public JsonResult desktop(HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()){

            String personNumber = jedis.hget(ConstantsRedisKey.DESKTOP, ConstantsRedisKey.DESKTOP_PERSON_NUMBER);
            String movieNumber = jedis.hget(ConstantsRedisKey.DESKTOP, ConstantsRedisKey.DESKTOP_MOVIE_NUMBER);
            String hotNumber = jedis.hget(ConstantsRedisKey.DESKTOP, ConstantsRedisKey.DESKTOP_HOT_NUMBER);



            map.put("personNumber",personNumber);
            map.put("movieNumber",movieNumber);
            map.put("hotNumber",hotNumber);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new JsonResult().setSuccess(true).setObj(map);
    }


    @RequestMapping("/loadMenu")
    @ResponseBody
    public List<MenuDataVo> loadMenu(HttpServletRequest request) {
        SystemUser userInfo = (SystemUser) SecurityUtils.getSubject().getSession().getAttribute(ConstantsRedisKey.SESSION_USER_INFO);
        List<MenuDataVo> userList = systemMenuService.findUserList(userInfo);
        return userList;
    }

    @RequestMapping("/setPassword")
    @ResponseBody
    @CommonLog(name = "修改管理员密码")
    public JsonResult setPassword(HttpServletRequest request) {
        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        String confirmNewPwd = request.getParameter("confirmNewPwd");
        return systemUserService.setPassword(oldPwd,newPwd,confirmNewPwd);
    }



}
