package com.oauth.controller;

import com.common.constants.ConstantsRedisKey;
import com.common.model.JsonResult;
import com.github.pagehelper.PageInfo;
import com.movie.model.Movie;
import com.movie.service.MovieService;
import com.oauth.model.SystemMenu;
import com.oauth.model.SystemUser;
import com.oauth.service.SystemMenuService;
import com.oauth.service.SystemUserService;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/17 16:59
 * @Description:
 */
@Api(value = "登录-首页")
@Controller
@RequestMapping("/")
public class LoginController {

    @Resource
    private SystemUserService systemUserService;


    @RequestMapping("index")
    public String index(HttpServletRequest request) {
        return "login";
    }


    @RequestMapping("/loginService")
    @ResponseBody
    public JsonResult loginService(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            SystemUser userInfo = systemUserService.findByUserName(username);
            if (userInfo != null) {
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);

                Subject currentUser = SecurityUtils.getSubject();
                if (!currentUser.isAuthenticated()) {
                    currentUser.login(token);// 验证角色和权限
                    System.out.println("登录成功");
                    SecurityUtils.getSubject().getSession().setAttribute(ConstantsRedisKey.SESSION_USER_INFO, userInfo);
                }
            }
            return new JsonResult().setSuccess(true).setMsg("登录成功");
        } catch (Exception e) {

        }
        return new JsonResult().setObj(username).setMsg("用户名/密码错误!");
    }

    @RequestMapping("main")
    public String main(HttpServletRequest request) {
        return "main";
    }



    @RequestMapping("logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        return ;
    }





}
