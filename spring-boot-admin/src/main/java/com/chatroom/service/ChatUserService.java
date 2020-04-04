package com.chatroom.service;


import com.chatroom.model.ChatUser;
import com.common.model.PageResult;
import java.util.Map;

/**
 * <p> ChatUserService </p>
 * @author:         jidn
 * @Date :          2020-04-03 16:10:39 
 */
public interface ChatUserService {

   PageResult findPageBySql(Map<String,String> param);
}
