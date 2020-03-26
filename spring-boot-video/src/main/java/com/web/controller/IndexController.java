package com.web.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2020/1/2 15:13
 * @Description:
 */
@Api(value = "登录-首页")
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("index")
    public String index(HttpServletRequest request) {
        return "index";
    }
}
