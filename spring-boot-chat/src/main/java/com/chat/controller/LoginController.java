package com.chat.controller;

import com.chat.common.utils.JsonResult;
import com.chat.service.ChatUserService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(value = "登录-首页")
@Controller
@RequestMapping("/")
public class LoginController {

    @Resource
    private ChatUserService chatUserService;

    @RequestMapping("index")
    public String index(HttpServletRequest request) {
        return "login";
    }


    @PostMapping("/login")
    @ResponseBody
    public JsonResult login(HttpServletRequest request, HttpSession session){
        String username = request.getParameter("username");
        String password = request.getParameter("password");


        JsonResult login = chatUserService.login(username, password);
        if(login.getSuccess()){
            return new JsonResult().setMsg("账号或者密码错误");
        }
        //session.setAttribute("userid",userid);
        return new JsonResult().setSuccess(true).setMsg("success");
    }
}
