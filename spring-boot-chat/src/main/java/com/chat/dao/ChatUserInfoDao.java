package com.chat.dao;

import com.chat.model.ChatUserInfo;
import com.mvc.base.dao.BaseDao;

import java.util.List;
import java.util.Map;


/**
 * <p> ChatUserInfoDao </p>
 * @author: jidn
 * @Date :  2020-04-03 16:11:00
 */
public interface ChatUserInfoDao extends  BaseDao<ChatUserInfo, Integer> {


    List<ChatUserInfo> findPageBySql(Map<String, Object> paraMap);
}
