package com.chat.controller;

import com.alibaba.fastjson.JSONObject;
import com.chat.model.ChatMessage;
import com.chat.service.ChatUserService;
import com.chat.vo.ChatFriendVo;
import com.chat.vo.ChatUserInfoVo;
import com.common.constants.ConstantsRedisKey;
import com.common.utils.EmojiFilter;
import com.common.utils.JsonResult;
import com.util.PropertiesUtils;
import com.util.SpringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Copyright © 北京互融时代软件有限公司
 * @Author: Jidn
 * @Date: 2020/4/7 14:38
 * @Description:
 */
@Api(value = "聊天室")
@Controller
@RequestMapping("/chat")
public class ChatController {

    private ChatUserService chatUserService = (ChatUserService) SpringUtil.getBean(PropertiesUtils.APP.getProperty("app.service"));


    @ApiOperation(value = "聊天大厅-好友列表", httpMethod = "POST", notes = "聊天大厅-好友列表")
    @PostMapping("/friends")
    @ResponseBody
    @RequiresAuthentication
    public JsonResult friends(HttpSession session){
        ChatUserInfoVo chatUserInfoVo = (ChatUserInfoVo) SecurityUtils.getSubject().getSession().getAttribute(ConstantsRedisKey.SESSION_USER_INFO);
        List<ChatFriendVo> list = chatUserService.findFriendListByUserId(chatUserInfoVo.getUserid());
        if(list != null && list.size() > 0){
            return new JsonResult().setSuccess(true).setObj(list);
        }
        return new JsonResult();
    }


    @ApiOperation(value = "聊天大厅-查找好友", httpMethod = "POST", notes = "个人中心-查找好友")
    @PostMapping("/searchFriend")
    @ResponseBody
    @RequiresAuthentication
    public JsonResult searchFriend(
                        @ApiParam(name = "username", value = "用户账号", required = true) @RequestParam("username") String username
                        ){
        username= EmojiFilter.filterEmoji(username);
        ChatUserInfoVo chatUser = chatUserService.getChatUser(username);
        if(chatUser == null){
            return new JsonResult().setSuccess(false);
        }
        chatUser.setPassword("");
        chatUser.setSalt("");
        if(chatUser == null){
            return new JsonResult().setMsg("未查到此用户");
        }
        return new JsonResult().setSuccess(true).setObj(chatUser);
    }


    @ApiOperation(value = "聊天大厅-添加好友", httpMethod = "POST", notes = "个人中心-添加好友")
    @PostMapping("/addFriend")
    @ResponseBody
    @RequiresAuthentication
    public JsonResult addFriend(
            @ApiParam(name = "username", value = "用户账号", required = true) @RequestParam("username") String username
    ){
        username= EmojiFilter.filterEmoji(username);
        ChatUserInfoVo chatUserInfoVo = (ChatUserInfoVo) SecurityUtils.getSubject().getSession().getAttribute(ConstantsRedisKey.SESSION_USER_INFO);
        if(chatUserInfoVo.getUserid().equals(username)){
            return new JsonResult().setSuccess(true).setMsg("不能添加自己");
        }
        ChatUserInfoVo chatUser = chatUserService.getChatUser(username);
        if(chatUser == null){
            return new JsonResult().setMsg("未查到此用户");
        }
        chatUserService.addUserFriendRelation(chatUserInfoVo.getUsername(),chatUser.getUsername(), chatUserInfoVo.getUserid(),chatUser.getUserid());
        return new JsonResult().setSuccess(true).setMsg("success");
    }



    @ApiOperation(value = "聊天大厅-查找好友聊天记录", httpMethod = "POST", notes = "个人中心-查找好友聊天记录")
    @PostMapping("/findChatHistory")
    @ResponseBody
    @RequiresAuthentication
    public JsonResult findChatHistory(
            @ApiParam(name = "friendUserId", value = "好友ID", required = true) @RequestParam("friendUserId") String friendUserId
                    ){
        ChatUserInfoVo chatUserInfoVo = (ChatUserInfoVo) SecurityUtils.getSubject().getSession().getAttribute(ConstantsRedisKey.SESSION_USER_INFO);
        List<ChatMessage> history = chatUserService.findChatHistory(chatUserInfoVo.getUserid(), friendUserId);
        return new JsonResult().setSuccess(true).setMsg("success").setObj(history);
    }


    @ApiOperation(value = "聊天大厅-上传聊天图片", httpMethod = "POST", notes = "个人中心-上传聊天图片")
    @PostMapping("/chatImg")
    @ResponseBody
    @RequiresAuthentication
    public JsonResult chatImg(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        JSONObject res = new JSONObject();
        JSONObject resUrl = new JSONObject();
        String filename = UUID.randomUUID().toString().replaceAll("-", "");
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String filenames = filename + "." + ext;
        file.transferTo(new File("D:/ifeng/" + filenames));
        resUrl.put("src", "/pic/" + filenames);
        return new JsonResult().setSuccess(true).setData(resUrl);
    }

}
