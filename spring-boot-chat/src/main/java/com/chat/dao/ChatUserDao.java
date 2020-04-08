package com.chat.dao;

import com.chat.model.ChatUser;
import com.chat.vo.ChatUserInfoVo;
import com.mvc.base.dao.BaseDao;

import java.util.List;


/**
 * <p> ChatUserDao </p>
 * @author: jidn
 * @Date :  2020-04-03 16:10:39
 */
public interface ChatUserDao extends  BaseDao<ChatUser, Integer> {


    List<ChatUserInfoVo> findChatUserInfoList();
}
