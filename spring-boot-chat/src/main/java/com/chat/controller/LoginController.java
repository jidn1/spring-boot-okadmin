package com.chat.controller;

import com.chat.runnable.LoginRunnable;
import com.chat.vo.ChatUserInfoVo;
import com.common.constants.ConstantsRedisKey;
import com.common.utils.ChatUtils;
import com.common.utils.JsonResult;
import com.chat.service.ChatUserService;
import com.redis.BaseRedis;
import com.thread.ThreadPool;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Api(value = "登录-首页")
@Controller
@RequestMapping("/")
public class LoginController {

    @Resource
    private ChatUserService chatUserService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        return "login";
    }


    @PostMapping("/loginService")
    @ResponseBody
    public JsonResult loginService(HttpServletRequest request, HttpSession session,HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            ChatUserInfoVo chatUser = chatUserService.getChatUser(username);
            if (chatUser != null) {
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                Subject currentUser = SecurityUtils.getSubject();
                if (!currentUser.isAuthenticated()) {
                    currentUser.login(token);
                    SecurityUtils.getSubject().getSession().setAttribute(ConstantsRedisKey.SESSION_USER_INFO, chatUser);
                    ThreadPool.exe(new LoginRunnable(chatUser.getUserid()));
                    ChatUtils.createSession(jedis,chatUser,response);
                }
                return new JsonResult().setSuccess(true).setMsg("登录成功").setObj(chatUser.getUserid());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult().setSuccess(false).setMsg("账号不存在");
    }

    @PostMapping("/register")
    @ResponseBody
    public JsonResult register(HttpServletRequest request, HttpSession session) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        return chatUserService.register(username, password);
    }


    @RequestMapping("logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        return ;
    }

}
