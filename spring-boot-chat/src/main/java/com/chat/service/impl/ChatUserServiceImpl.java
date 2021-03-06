package com.chat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chat.dao.ChatMessageDao;
import com.chat.dao.ChatUserDao;
import com.chat.model.*;
import com.chat.vo.ChatFriendVo;
import com.chat.vo.ChatGroupVo;
import com.chat.vo.ChatMsgVo;
import com.chat.vo.ChatUserInfoVo;
import com.common.constants.ConstantsRedisKey;
import com.common.utils.ChatUtils;
import com.common.utils.DateUtils;
import com.common.utils.JsonResult;
import com.common.utils.PasswordHelper;
import com.chat.service.ChatUserService;
import com.db.Criteria;
import com.redis.BaseRedis;
import com.common.utils.UUIDUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> ChatUserService </p>
 *
 * @author: jidn
 * @Date :   2020-04-03 16:10:39
 */
@Service("chatUserService")
public class ChatUserServiceImpl implements ChatUserService {

    @Resource
    private ChatUserDao chatUserDao;

    @Resource
    private ChatMessageDao chatMessageDao;

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

            Criteria<ChatUser, Long> userCriteria = new Criteria<>(ChatUser.class);
            userCriteria.addFilter("username=", username);
            ChatUser chatUser1 = userCriteria.findByExample();
            if (chatUser1 != null) {
              return  new JsonResult().setSuccess(false).setMsg("账号已存在,请重新创建");
            }
            ChatUser chatUser = new ChatUser();
            chatUser.setUsername(username);
            chatUser.setPassword(s);
            chatUser.setSalt(salt);
            chatUser.setUserId(userId);
            userCriteria.save(chatUser);

            Criteria<ChatUserInfo, Long> userInfoCriteria = new Criteria<>(ChatUserInfo.class);
            ChatUserInfo chatUserInfo = new ChatUserInfo();
            chatUserInfo.setUserid(userId);
            chatUserInfo.setNickName(ChatUtils.getNickName());
            chatUserInfo.setAvatarImg(ConstantsRedisKey.DEFAULT_AVATAR);
            chatUserInfo.setExpirationTime(DateUtils.calcAddDigitalDaysLater(1));
            chatUserInfo.setGender(2);
            chatUserInfo.setIfdelete(0);
            chatUserInfo.setSignature(ConstantsRedisKey.DEFAULT_SIGNATURE);
            userInfoCriteria.save(chatUserInfo);
            ChatUserInfoVo userInfoVo = ChatUtils.convertRedisVo(chatUser, chatUserInfo);
            jedis.hset(ConstantsRedisKey.CHAT_USER_LIST, username, JSONObject.toJSONString(userInfoVo));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult().setSuccess(true).setMsg("success");
    }

    @Override
    public void initChatUserRedis() {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            List<ChatUserInfoVo> chatUserInfoList = chatUserDao.findChatUserInfoList();
            for (ChatUserInfoVo chatUserInfoVo : chatUserInfoList) {
                jedis.hset(ConstantsRedisKey.CHAT_USER_LIST, chatUserInfoVo.getUsername(), JSONObject.toJSONString(chatUserInfoVo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ChatFriendVo> findFriendListByUserId(String userId) {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            String str = jedis.hget(ConstantsRedisKey.CHAT_USER_FRIEND, userId);
            if(!StringUtils.isEmpty(str)){
                List<ChatFriendVo> friendVoList = JSONObject.parseArray(str, ChatFriendVo.class);
                return friendVoList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addUserFriendRelation(String username,String friendusername,String userId, String friendId) {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            Criteria<ChatFriend, Long> userFriendCriteria = new Criteria<>(ChatFriend.class);
            userFriendCriteria.addFilter("userId=", userId);
            userFriendCriteria.addFilter("friendUserId=", friendId);
            ChatFriend chatFriend = userFriendCriteria.findByExample();
            if (chatFriend == null) {
                chatFriend = new ChatFriend();
                chatFriend.setUserId(userId);
                chatFriend.setFriendUserId(friendId);
                chatFriend.setStatus(1);
                userFriendCriteria.save(chatFriend);

                ChatFriend chatFriend1 = new ChatFriend();
                chatFriend1.setUserId(friendId);
                chatFriend1.setFriendUserId(userId);
                chatFriend1.setStatus(1);
                userFriendCriteria.save(chatFriend1);
            }
            List<ChatFriendVo> friendVoList = new ArrayList<>();
            Criteria<ChatUserInfo, Long> userInfoCriteria = new Criteria<>(ChatUserInfo.class);
            userInfoCriteria.addFilter("userid=",friendId);
            ChatUserInfo chatUserInfo = userInfoCriteria.findByExample();
            ChatFriendVo chatFriendVo = ChatUtils.convertRedisFriendVo(userId, chatUserInfo);
            String str = jedis.hget(ConstantsRedisKey.CHAT_USER_FRIEND, userId);
            if(!StringUtils.isEmpty(str)){
                friendVoList = JSONObject.parseArray(str, ChatFriendVo.class);
            }
            friendVoList.add(chatFriendVo);
            jedis.hset(ConstantsRedisKey.CHAT_USER_FRIEND, userId,JSONObject.toJSONString(friendVoList));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ChatMessage> findChatHistory(String userId, String friendId) {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            String str = jedis.hget(ConstantsRedisKey.CHAT_USER_HISTORY + ":" + userId, friendId);
            if (!StringUtils.isEmpty(str)) {
                List<ChatMessage> chatHistory = chatMessageDao.findChatHistory(userId, friendId);
                if (chatHistory != null && chatHistory.size() > 0) {
                    jedis.hset(ConstantsRedisKey.CHAT_USER_HISTORY + ":" + userId, friendId, JSONObject.toJSONString(chatHistory));
                    jedis.hset(ConstantsRedisKey.CHAT_USER_HISTORY + ":" + friendId, userId, JSONObject.toJSONString(chatHistory));
                    return chatHistory;
                }
            }
            List<ChatMessage> messageList = JSONObject.parseArray(str, ChatMessage.class);
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
            Criteria<ChatMessage,Integer> criteria = new Criteria<>(ChatMessage.class);
            criteria.save(chatMessage);

            List<ChatMessage> chatHistory = new ArrayList<>();
            String str = jedis.hget(ConstantsRedisKey.CHAT_USER_HISTORY + ":" + chatMsgVo.getFromUserId(), chatMsgVo.getToUserId());
            if(!StringUtils.isEmpty(str)){
                chatHistory = JSONObject.parseArray(str, ChatMessage.class);
            }

            chatHistory.add(chatMessage);
            jedis.hset(ConstantsRedisKey.CHAT_USER_HISTORY + ":" + chatMsgVo.getFromUserId(), chatMsgVo.getToUserId(), JSONObject.toJSONString(chatHistory));
            jedis.hset(ConstantsRedisKey.CHAT_USER_HISTORY + ":" + chatMsgVo.getToUserId(), chatMsgVo.getFromUserId(), JSONObject.toJSONString(chatHistory));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void logout() {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            ChatUserInfoVo chatUser =(ChatUserInfoVo) SecurityUtils.getSubject().getSession().getAttribute(ConstantsRedisKey.SESSION_USER_INFO);
            jedis.hdel(ConstantsRedisKey.CHAT_USER_LIST,chatUser.getUsername());
            jedis.hdel(ConstantsRedisKey.CHAT_USER_FRIEND, chatUser.getUserid());

            Criteria<ChatUserInfo, Long> criteria = new Criteria<>(ChatUserInfo.class);
            criteria.addFilter("userId=", chatUser.getUserid());
            criteria.deleteByExample();

            Criteria<ChatUser, Long> criteria1 = new Criteria<>(ChatUser.class);
            criteria1.addFilter("userId=", chatUser.getUserid());
            criteria1.deleteByExample();

            Criteria<ChatFriend, Long> userFriendCriteria = new Criteria<>(ChatFriend.class);
            userFriendCriteria.addFilter("userId=", chatUser.getUserid());
            userFriendCriteria.deleteByExample();
            userFriendCriteria.addFilter("friendUserId=", chatUser.getUserid());
            userFriendCriteria.deleteByExample();
        } catch (Exception e){
        }
    }


}

