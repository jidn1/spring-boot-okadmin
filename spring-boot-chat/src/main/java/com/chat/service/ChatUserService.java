package com.chat.service;



import com.chat.model.ChatGroupMessage;
import com.chat.model.ChatMessage;
import com.chat.model.ChatUser;
import com.chat.vo.ChatFriendVo;
import com.chat.vo.ChatGroupVo;
import com.chat.vo.ChatMsgVo;
import com.chat.vo.ChatUserInfoVo;
import com.common.utils.JsonResult;

import java.util.List;
import java.util.Map;

/**
 * <p> ChatUserService </p>
 * @author:         jidn
 * @Date :          2020-04-03 16:10:39
 */
public interface ChatUserService {

   /**
    * 用户名获取
    * @param username
    * @return
    */
   ChatUserInfoVo getChatUser(String username);

   /**
    * 注册
    * @param username
    * @param password
    * @return
    */
   JsonResult register(String username, String password);

   /**
    * redis 初始化 用户信息
    */
   void initChatUserRedis();

   /**
    * 根据 uid 查询好友
    * @param userId
    * @return
    */
   List<ChatFriendVo> findFriendListByUserId(String userId);

   /**
    * 添加好友
    * @param userId
    * @param friendId
    */
   void addUserFriendRelation(String username,String friendusername,String userId,String friendId);

   /**
    * 查找历史聊天记录
    * @param userId
    * @param friendId
    * @return
    */
   List<ChatMessage> findChatHistory(String userId, String friendId);

   /**
    * 保存消息记录
    * @param chatMsgVo
    */
   void saveMsg(ChatMsgVo chatMsgVo);

   /**
    * 退出
    */
   void logout();

}
