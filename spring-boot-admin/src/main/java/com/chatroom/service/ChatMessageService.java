package com.chatroom.service;


import com.chatroom.model.ChatMessage;
import com.common.model.PageResult;
import java.util.Map;

/**
 * <p> ChatMessageService </p>
 * @author:         jidn
 * @Date :          2020-04-03 18:34:52 
 */
public interface ChatMessageService {

   PageResult findPageBySql(Map<String,String> param);
}
