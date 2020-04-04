package com.chatroom.service;


import com.chatroom.model.ChatFriend;
import com.common.model.PageResult;
import java.util.Map;

/**
 * <p> ChatFriendService </p>
 * @author:         jidn
 * @Date :          2020-04-03 18:35:16 
 */
public interface ChatFriendService {

   PageResult findPageBySql(Map<String,String> param);
}
