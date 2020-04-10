package com.chat.controller;

import com.alibaba.fastjson.JSONObject;
import com.chat.model.ChatFriend;
import com.chat.model.ChatUser;
import com.chat.model.ChatUserInfo;
import com.chat.runnable.LoginRunnable;
import com.chat.vo.ChatUserInfoVo;
import com.common.constants.ConstantsRedisKey;
import com.common.utils.ChatUtils;
import com.common.utils.JsonResult;
import com.chat.service.ChatUserService;
import com.db.Criteria;
import com.redis.BaseRedis;
import com.thread.ThreadPool;
import com.util.PropertiesUtils;
import com.util.SpringUtil;
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

    private ChatUserService chatUserService = (ChatUserService)SpringUtil.getBean(PropertiesUtils.APP.getProperty("app.service"));

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        return "login";
    }


    @RequestMapping("/loginService")
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

    @RequestMapping("/register")
    @ResponseBody
    public JsonResult register(HttpServletRequest request, HttpSession session) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        return chatUserService.register(username, password);
    }


    @RequestMapping("logout")
    @ResponseBody
    public JsonResult logout(HttpServletRequest request, HttpServletResponse response) {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            ChatUserInfoVo chatUser =(ChatUserInfoVo) SecurityUtils.getSubject().getSession().getAttribute(ConstantsRedisKey.SESSION_USER_INFO);
            jedis.hdel(ConstantsRedisKey.CHAT_USER_LIST,chatUser.getUsername());
            jedis.hdel(ConstantsRedisKey.CHAT_USER_FRIEND, chatUser.getUserid());

            Criteria<ChatUserInfo, Long> criteria = new Criteria<>(ChatUserInfo.class);
            criteria.addFilter("userId=", chatUser.getUserid());
            criteria.deleteByExample();

            Criteria<ChatUser, Long> criteria1 = new Criteria<>(ChatUser.class);
            criteria1.addFilter("userId=", chatUser.getUserid());
            criteria1.deleteByExample();

            Criteria<ChatFriend, Long> userFriendCriteria = new Criteria<>(ChatFriend.class);
            userFriendCriteria.addFilter("userId=", chatUser.getUserid());
            userFriendCriteria.deleteByExample();
            userFriendCriteria.addFilter("friendUserId=", chatUser.getUserid());
            userFriendCriteria.deleteByExample();
        } catch (Exception e){
        }
        return new JsonResult().setSuccess(true);
    }

}
