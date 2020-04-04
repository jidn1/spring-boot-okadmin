package com.chatroom.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.util.HttpServletRequestUtils;
import com.common.model.JsonResult;
import com.common.model.PageResult;
import io.swagger.annotations.Api;
import com.db.Criteria;
import org.springframework.web.bind.annotation.*;
import com.chatroom.model.ChatMessage;
import com.chatroom.service.ChatMessageService;
import java.util.Map;

/**
 * @Copyright:   正经吉
 * @author:      jidn
 * @version:     V1.0
 * @Date:        2020-04-03 18:34:52 
 */
@Api(value = "")
@RestController
@RequestMapping("/chatMessage")
public class ChatMessageController{

	@Resource
	private ChatMessageService chatMessageService;

	@RequestMapping("/list")
	@ResponseBody
	public PageResult list(HttpServletRequest request) {
		Map<String, String> params = HttpServletRequestUtils.getParams(request);
		return chatMessageService.findPageBySql(params);
	}


    @RequestMapping("/save")
    @ResponseBody
    public JsonResult save(ChatMessage chatMessage) {
        Criteria<ChatMessage,Integer> criteria = new Criteria<>(ChatMessage.class);
        criteria.save(chatMessage);
        return new JsonResult().setSuccess(true).setMsg("success");
    }

	@RequestMapping("/modify")
	@ResponseBody
	public JsonResult modify(HttpServletRequest request,ChatMessage chatMessage) {
		Criteria<ChatMessage,Integer> criteria = new Criteria<>(ChatMessage.class);
		criteria.update(chatMessage);
		return new JsonResult().setSuccess(true).setMsg("success");
	}


	@RequestMapping("/remove")
	@ResponseBody
	public JsonResult remove(String idsStr) {
		Criteria<ChatMessage,Integer> criteria = new Criteria<>(ChatMessage.class);
        String[] idss = idsStr.split(",");
        for(String id : idss){
			criteria.deleteByPrimary(Integer.valueOf(id));
        }
		return new JsonResult().setSuccess(true).setMsg("success");
	}

}
