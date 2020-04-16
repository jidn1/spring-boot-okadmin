package com.chat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chat.model.*;
import com.chat.service.ChatUserService;
import com.chat.vo.*;
import com.common.constants.ConstantsRedisKey;
import com.common.utils.ChatUtils;
import com.common.utils.DateUtils;
import com.common.utils.JsonResult;
import com.common.utils.PasswordHelper;
import com.db.Criteria;
import com.redis.BaseRedis;
import com.common.utils.UUIDUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright © 北京互融时代软件有限公司
 * @Author: Jidn
 * @Date: 2020/4/10 15:40
 * @Description:
 */
@Service("chatUserRedisService")
public class ChatUserRedisServiceImpl implements ChatUserService {

    @Override
    public ChatUserInfoVo getChatUser(String username) {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            String user = jedis.hget(ConstantsRedisKey.CHAT_USER_LIST, username);
            if (!StringUtils.isEmpty(user)) {
                ChatUserInfoVo chatUser = JSONObject.parseObject(user, ChatUserInfoVo.class);
                return chatUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JsonResult register(String username, String password) {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            String salt = UUIDUtil.getUUID();
            String userId = UUIDUtil.getUUID();
            PasswordHelper passwordHelper = new PasswordHelper();

            String s = passwordHelper.encryString(password, salt);

            String user = jedis.hget(ConstantsRedisKey.CHAT_USER_LIST, username);
            if (!StringUtils.isEmpty(user)) {
                return new JsonResult().setSuccess(false).setMsg("账号已存在,请重新创建");
            }
            ChatUser chatUser = new ChatUser();
            chatUser.setUsername(username);
            chatUser.setPassword(s);
            chatUser.setSalt(salt);
            chatUser.setUserId(userId);

            ChatUserInfo chatUserInfo = new ChatUserInfo();
            chatUserInfo.setUserid(userId);
            chatUserInfo.setNickName(ChatUtils.getNickName());
            chatUserInfo.setAvatarImg(ConstantsRedisKey.DEFAULT_AVATAR);
            chatUserInfo.setExpirationTime(DateUtils.calcAddDigitalDaysLater(1));
            chatUserInfo.setGender(2);
            chatUserInfo.setIfdelete(0);
            chatUserInfo.setSignature(ConstantsRedisKey.DEFAULT_SIGNATURE);

            ChatUserInfoVo userInfoVo = ChatUtils.convertRedisVo(chatUser, chatUserInfo);
            System.out.println("注册信息==" + JSONObject.toJSONString(userInfoVo));
            jedis.hset(ConstantsRedisKey.CHAT_USER_LIST, username, JSONObject.toJSONString(userInfoVo));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult().setSuccess(true).setMsg("success");
    }

    @Override
    public void initChatUserRedis() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ChatFriendVo> findFriendListByUserId(String userId) {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            String str = jedis.hget(ConstantsRedisKey.CHAT_USER_FRIEND, userId);
            if (!StringUtils.isEmpty(str)) {
                List<ChatFriendVo> friendVoList = JSONObject.parseArray(str, ChatFriendVo.class);
                return friendVoList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addUserFriendRelation(String username, String friendusername, String userId, String friendId) {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            List<ChatFriendVo> friendVoList = new ArrayList<>();
            List<ChatFriendVo> friendVoList1 = new ArrayList<>();
            boolean flag = true;
            String friendStr = jedis.hget(ConstantsRedisKey.CHAT_USER_FRIEND, userId);
            String friendStr1 = jedis.hget(ConstantsRedisKey.CHAT_USER_FRIEND, friendId);
            if (!StringUtils.isEmpty(friendStr)) {
                friendVoList = JSONObject.parseArray(friendStr, ChatFriendVo.class);
            }
            if (!StringUtils.isEmpty(friendStr1)) {
                friendVoList1 = JSONObject.parseArray(friendStr1, ChatFriendVo.class);
            }
            for (ChatFriendVo f : friendVoList) {
                if (f.getFriendUserId().equals(friendId)) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                String user = jedis.hget(ConstantsRedisKey.CHAT_USER_LIST, friendusername);
                ChatUserInfoVo chatUser = JSONObject.parseObject(user, ChatUserInfoVo.class);
                ChatFriendVo chatFriendVo = ChatUtils.convertRedisFriendVo(userId, chatUser);

                friendVoList.add(chatFriendVo);
                jedis.hset(ConstantsRedisKey.CHAT_USER_FRIEND, userId, JSONObject.toJSONString(friendVoList));

                String friendUserStr = jedis.hget(ConstantsRedisKey.CHAT_USER_LIST, username);
                ChatUserInfoVo friendUser = JSONObject.parseObject(friendUserStr, ChatUserInfoVo.class);
                ChatFriendVo chatFriendVo1 = ChatUtils.convertRedisFriendVo(friendId, friendUser);

                friendVoList1.add(chatFriendVo1);
                jedis.hset(ConstantsRedisKey.CHAT_USER_FRIEND, friendId, JSONObject.toJSONString(friendVoList1));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ChatMessage> findChatHistory(String userId, String friendId) {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            String str = jedis.hget(ConstantsRedisKey.CHAT_USER_HISTORY + ":" + userId, friendId);
            List<ChatMessage> messageList = new ArrayList<>();
            if(!StringUtils.isEmpty(str)){
                messageList = JSONObject.parseArray(str, ChatMessage.class);
            }
            return messageList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void saveMsg(ChatMsgVo chatMsgVo) {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            ChatMessage chatMessage = new ChatMessage();
            BeanUtils.copyProperties(chatMsgVo, chatMessage);

            List<ChatMessage> chatHistory = new ArrayList<>();
            String str = jedis.hget(ConstantsRedisKey.CHAT_USER_HISTORY + ":" + chatMsgVo.getFromUserId(), chatMsgVo.getToUserId());
            if (!StringUtils.isEmpty(str)) {
                chatHistory = JSONObject.parseArray(str, ChatMessage.class);
            }

            chatHistory.add(chatMessage);
            jedis.hset(ConstantsRedisKey.CHAT_USER_HISTORY + ":" + chatMsgVo.getFromUserId(), chatMsgVo.getToUserId(), JSONObject.toJSONString(chatHistory));
            jedis.hset(ConstantsRedisKey.CHAT_USER_HISTORY + ":" + chatMsgVo.getToUserId(), chatMsgVo.getFromUserId(), JSONObject.toJSONString(chatHistory));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void logout() {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            ChatUserInfoVo chatUser = (ChatUserInfoVo) SecurityUtils.getSubject().getSession().getAttribute(ConstantsRedisKey.SESSION_USER_INFO);

            jedis.hdel(ConstantsRedisKey.TOKEN, chatUser.getUserid());
            jedis.hdel(ConstantsRedisKey.CHAT_USER_FRIEND, chatUser.getUserid());
            jedis.del(ConstantsRedisKey.CHAT_USER_HISTORY + ":" + chatUser.getUserid());
            jedis.hdel(ConstantsRedisKey.CHAT_USER_LIST, chatUser.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public JsonResult createGroup(String userId,String userName, String groupName) {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            String group = jedis.get(ConstantsRedisKey.CHAT_GROUP + ":" + groupName);
            if(!StringUtils.isEmpty(group)){
                return new JsonResult().setMsg("群已存在");
            }
            jedis.set(ConstantsRedisKey.CHAT_GROUP + ":" + groupName,"1");

            List<ChatGroupVo> list = new ArrayList<>();
            String s = jedis.get(ConstantsRedisKey.CHAT_GROUP_USER_SCOPE + ":" + userId);
            if(!StringUtils.isEmpty(s)){
                list = JSONObject.parseArray(s, ChatGroupVo.class);
            }
            list.add(ChatUtils.convertRedisChatGroupVo(userId,groupName));
            jedis.set(ConstantsRedisKey.CHAT_GROUP_USER_SCOPE + ":" + userId,JSONObject.toJSONString(list));

            List<ChatGroupMemberVo> memberVoList = new ArrayList<>();
            ChatGroupMemberVo memberVo = new ChatGroupMemberVo();
            memberVo.setGroupName(groupName);
            memberVo.setGroupMemberUserId(userId);
            memberVo.setGroupMemberUserName(userName);
            memberVoList.add(memberVo);
            jedis.hset(ConstantsRedisKey.CHAT_GROUP+":"+userId,groupName,JSONObject.toJSONString(memberVoList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult().setSuccess(true).setMsg("创建成功");
    }


    @Override
    public List<ChatGroupVo> findGroups(String userId){
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            List<ChatGroupVo> list = new ArrayList<>();
            String s = jedis.get(ConstantsRedisKey.CHAT_GROUP_USER_SCOPE + ":" + userId);
            if(!StringUtils.isEmpty(s)){
                list = JSONObject.parseArray(s, ChatGroupVo.class);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ChatGroupMessage> findGroupChatMsgHistory(String groupName) {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            List<ChatGroupMessage> list = new ArrayList<>();
            String s = jedis.hget(ConstantsRedisKey.CHAT_GROUP_HISTORY , groupName);
            if(!StringUtils.isEmpty(s)){
                list = JSONObject.parseArray(s, ChatGroupMessage.class);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
