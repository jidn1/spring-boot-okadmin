package com.chatroom.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.util.HttpServletRequestUtils;
import com.common.model.JsonResult;
import com.common.model.PageResult;
import io.swagger.annotations.Api;
import com.db.Criteria;
import org.springframework.web.bind.annotation.*;
import com.chatroom.model.ChatFriend;
import com.chatroom.service.ChatFriendService;
import java.util.Map;

/**
 * @Copyright:   正经吉
 * @author:      jidn
 * @version:     V1.0
 * @Date:        2020-04-03 18:35:16 
 */
@Api(value = "")
@RestController
@RequestMapping("/chatFriend")
public class ChatFriendController{

	@Resource
	private ChatFriendService chatFriendService;

	@RequestMapping("/list")
	@ResponseBody
	public PageResult list(HttpServletRequest request) {
		Map<String, String> params = HttpServletRequestUtils.getParams(request);
		return chatFriendService.findPageBySql(params);
	}


    @RequestMapping("/save")
    @ResponseBody
    public JsonResult save(ChatFriend chatFriend) {
        Criteria<ChatFriend,Integer> criteria = new Criteria<>(ChatFriend.class);
        criteria.save(chatFriend);
        return new JsonResult().setSuccess(true).setMsg("success");
    }

	@RequestMapping("/modify")
	@ResponseBody
	public JsonResult modify(HttpServletRequest request,ChatFriend chatFriend) {
		Criteria<ChatFriend,Integer> criteria = new Criteria<>(ChatFriend.class);
		criteria.update(chatFriend);
		return new JsonResult().setSuccess(true).setMsg("success");
	}


	@RequestMapping("/remove")
	@ResponseBody
	public JsonResult remove(String idsStr) {
		Criteria<ChatFriend,Integer> criteria = new Criteria<>(ChatFriend.class);
        String[] idss = idsStr.split(",");
        for(String id : idss){
			criteria.deleteByPrimary(Integer.valueOf(id));
        }
		return new JsonResult().setSuccess(true).setMsg("success");
	}

}
