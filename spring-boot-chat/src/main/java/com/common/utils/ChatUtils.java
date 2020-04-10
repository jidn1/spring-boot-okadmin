package com.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chat.model.ChatUser;
import com.chat.model.ChatUserInfo;
import com.chat.vo.ChatFriendVo;
import com.chat.vo.ChatUserInfoVo;
import com.common.constants.ConstantsRedisKey;
import com.util.uuid.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Copyright © 北京互融时代软件有限公司
 * @Author: Jidn
 * @Date: 2020/4/7 12:38
 * @Description:
 */
public class ChatUtils {

    public static final String TOKEN_NAME = "chatOneToken";

    public static String getNickName() {
        return "小吉_" + UUIDUtil.generateShortUuid();
    }


    public static ChatUserInfoVo convertRedisVo(ChatUser chatUser, ChatUserInfo chatUserInfo){
        ChatUserInfoVo chatUserInfoVo = new ChatUserInfoVo();
        chatUserInfoVo.setUsername(chatUser.getUsername());
        chatUserInfoVo.setPassword(chatUser.getPassword());
        chatUserInfoVo.setUserid(chatUser.getUserId());
        chatUserInfoVo.setSalt(chatUser.getSalt());
        chatUserInfoVo.setNickName(chatUserInfo.getNickName());
        chatUserInfoVo.setAvatarImg(chatUserInfo.getAvatarImg());
        chatUserInfoVo.setGender(chatUserInfo.getGender());
        chatUserInfoVo.setSignature(chatUserInfo.getSignature());
        chatUserInfoVo.setExpirationTime(chatUserInfo.getExpirationTime());
        chatUserInfoVo.setIfdelete(chatUserInfo.getIfdelete());
        return chatUserInfoVo;
    }

    public static ChatFriendVo convertRedisFriendVo(String userId, ChatUserInfo chatUserInfo){
        ChatFriendVo chatFriendVo = new ChatFriendVo();
        chatFriendVo.setUserId(chatUserInfo.getUserid());
        chatFriendVo.setFriendUserId(userId);
        chatFriendVo.setNickName(chatUserInfo.getNickName());
        chatFriendVo.setAvatarImg(chatUserInfo.getAvatarImg());
        chatFriendVo.setStatus(1);
        chatFriendVo.setId(0L);
        return chatFriendVo;
    }

    public static ChatFriendVo convertRedisFriendVo(String userId, ChatUserInfoVo chatUserInfo){
        ChatFriendVo chatFriendVo = new ChatFriendVo();
        chatFriendVo.setUserId(chatUserInfo.getUserid());
        chatFriendVo.setFriendUserId(userId);
        chatFriendVo.setNickName(chatUserInfo.getNickName());
        chatFriendVo.setAvatarImg(chatUserInfo.getAvatarImg());
        chatFriendVo.setStatus(1);
        chatFriendVo.setId(0L);
        return chatFriendVo;
    }


    public static void createSession(Jedis jedis, ChatUserInfoVo chatUser, HttpServletResponse response){
        jedis.hset(ConstantsRedisKey.TOKEN,chatUser.getUserid(), JSONObject.toJSONString(chatUser));
        Cookie cookie = new Cookie(TOKEN_NAME, chatUser.getUserid());
        response.addCookie(cookie);
    }

    public static ChatUserInfoVo getSession(Jedis jedis, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (null != cookies && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(TOKEN_NAME)) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (StringUtils.isNotEmpty(token)) {
            String sessionStr = jedis.hget(ConstantsRedisKey.TOKEN,token);
            if (StringUtils.isNotEmpty(sessionStr)) {
                return JSON.parseObject(sessionStr, ChatUserInfoVo.class);
            }
        }
        return null;
    }

}
