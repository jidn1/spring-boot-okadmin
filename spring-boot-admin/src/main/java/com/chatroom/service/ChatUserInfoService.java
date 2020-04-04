package com.chatroom.service;


import com.chatroom.model.ChatUserInfo;
import com.common.model.JsonResult;
import com.common.model.PageResult;
import java.util.Map;

/**
 * <p> ChatUserInfoService </p>
 * @author:         jidn
 * @Date :          2020-04-03 16:11:00
 */
public interface ChatUserInfoService {

   /**
    * 分页查询
    * @param param
    * @return
    */
   PageResult findPageBySql(Map<String,String> param);

   JsonResult addChatUser(Map<String,String> param);
}
