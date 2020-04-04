package com.chatroom.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.util.HttpServletRequestUtils;
import com.common.model.JsonResult;
import com.common.model.PageResult;
import io.swagger.annotations.Api;
import com.db.Criteria;
import org.springframework.web.bind.annotation.*;
import com.chatroom.model.ChatUser;
import com.chatroom.service.ChatUserService;
import java.util.Map;

/**
 * @Copyright:   正经吉
 * @author:      jidn
 * @version:     V1.0
 * @Date:        2020-04-03 16:10:39 
 */
@Api(value = "")
@RestController
@RequestMapping("/chatUser")
public class ChatUserController{

	@Resource
	private ChatUserService chatUserService;

	@RequestMapping("/list")
	@ResponseBody
	public PageResult list(HttpServletRequest request) {
		Map<String, String> params = HttpServletRequestUtils.getParams(request);
		return chatUserService.findPageBySql(params);
	}


    @RequestMapping("/save")
    @ResponseBody
    public JsonResult save(ChatUser chatUser) {
        Criteria<ChatUser,Integer> criteria = new Criteria<>(ChatUser.class);
        criteria.save(chatUser);
        return new JsonResult().setSuccess(true).setMsg("success");
    }

	@RequestMapping("/modify")
	@ResponseBody
	public JsonResult modify(HttpServletRequest request,ChatUser chatUser) {
		Criteria<ChatUser,Integer> criteria = new Criteria<>(ChatUser.class);
		criteria.update(chatUser);
		return new JsonResult().setSuccess(true).setMsg("success");
	}


	@RequestMapping("/remove")
	@ResponseBody
	public JsonResult remove(String idsStr) {
		Criteria<ChatUser,Integer> criteria = new Criteria<>(ChatUser.class);
        String[] idss = idsStr.split(",");
        for(String id : idss){
			criteria.deleteByPrimary(Integer.valueOf(id));
        }
		return new JsonResult().setSuccess(true).setMsg("success");
	}

}
