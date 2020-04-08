package com.common.utils;

import com.chat.model.ChatUser;
import com.chat.model.ChatUserInfo;
import com.chat.vo.ChatFriendVo;
import com.chat.vo.ChatUserInfoVo;
import com.util.uuid.UUIDUtil;

/**
 * @Copyright © 北京互融时代软件有限公司
 * @Author: Jidn
 * @Date: 2020/4/7 12:38
 * @Description:
 */
public class ChatUtils {


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


}
