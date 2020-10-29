package com.chatroom.dao;

import com.mvc.base.dao.BaseDao;
import com.chatroom.model.ChatMessage;

import java.util.List;
import java.util.Map;


/**
 * <p> ChatMessageDao </p>
 * @author: jidn
 * @Date :  2020-04-03 18:34:52 
 */
public interface ChatMessageDao extends  BaseDao<ChatMessage, Integer> {


    List<ChatMessage> findPageBySql(Map<String,Object> paraMap);
}
