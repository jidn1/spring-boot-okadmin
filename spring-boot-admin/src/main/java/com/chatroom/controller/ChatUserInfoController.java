package com.chatroom.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.util.HttpServletRequestUtils;
import com.common.model.JsonResult;
import com.common.model.PageResult;
import io.swagger.annotations.Api;
import com.db.Criteria;
import org.springframework.web.bind.annotation.*;
import com.chatroom.model.ChatUserInfo;
import com.chatroom.service.ChatUserInfoService;
import java.util.Map;

/**
 * @Copyright:   正经吉
 * @author:      jidn
 * @version:     V1.0
 * @Date:        2020-04-03 16:11:00
 */
@Api(value = "")
@RestController
@RequestMapping("/chatUserInfo")
public class ChatUserInfoController{

	@Resource
	private ChatUserInfoService chatUserInfoService;

	@RequestMapping("/list")
	@ResponseBody
	public PageResult list(HttpServletRequest request) {
		Map<String, String> params = HttpServletRequestUtils.getParams(request);
		return chatUserInfoService.findPageBySql(params);
	}


    @RequestMapping("/save")
    @ResponseBody
    public JsonResult save(HttpServletRequest request) {
		Map<String, String> params = HttpServletRequestUtils.getParams(request);
        return chatUserInfoService.addChatUser(params);
    }

	@RequestMapping("/modify")
	@ResponseBody
	public JsonResult modify(HttpServletRequest request,ChatUserInfo chatUserInfo) {
		Criteria<ChatUserInfo,Integer> criteria = new Criteria<>(ChatUserInfo.class);
		criteria.update(chatUserInfo);
		return new JsonResult().setSuccess(true).setMsg("success");
	}


	@RequestMapping("/remove")
	@ResponseBody
	public JsonResult remove(String idsStr) {
		Criteria<ChatUserInfo,Integer> criteria = new Criteria<>(ChatUserInfo.class);
        String[] idss = idsStr.split(",");
        for(String id : idss){
			criteria.deleteByPrimary(Integer.valueOf(id));
        }
		return new JsonResult().setSuccess(true).setMsg("success");
	}

}
