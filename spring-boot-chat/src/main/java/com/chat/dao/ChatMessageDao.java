package com.chat.dao;

import com.chat.model.ChatMessage;
import com.mvc.base.dao.BaseDao;

import java.util.List;


/**
 * <p> ChatMessageDao </p>
 * @author: jidn
 * @Date :  2020-04-03 18:34:52
 */
public interface ChatMessageDao extends  BaseDao<ChatMessage, Integer> {

    /**
     * 查找聊天记录
     * @param userId
     * @param friendUserId
     * @return
     */
    List<ChatMessage> findChatHistory(String userId,String friendUserId);
}
