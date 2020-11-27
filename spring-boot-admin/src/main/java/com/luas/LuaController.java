package com.luas;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "登录-首页")
@CrossOrigin
@RestController
@RequestMapping("/movie")
public class LuaController {

    @RequestMapping("/list")
    public void ping(){
        List<String> keys = new ArrayList<>();
        keys.add("buyPlan");
        keys.add("buyPlan");

        List<String> argvs = new ArrayList<>();
        argvs.add("67");
        argvs.add("234211");
        argvs.add("68");
        argvs.add("102432");
        LuaUtil.setCall(LuaResource.LUA_DEMO, keys, argvs);
    }
}
