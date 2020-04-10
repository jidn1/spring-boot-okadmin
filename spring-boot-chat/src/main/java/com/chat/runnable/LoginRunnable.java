package com.chat.runnable;

import com.alibaba.fastjson.JSONObject;
import com.chat.model.ChatFriend;
import com.chat.model.ChatUserInfo;
import com.chat.vo.ChatFriendVo;
import com.common.constants.ConstantsRedisKey;
import com.common.utils.ChatUtils;
import com.db.Criteria;
import com.redis.BaseRedis;
import com.util.PropertiesUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright © 北京互融时代软件有限公司
 * @Author: Jidn
 * @Date: 2020/4/7 17:37
 * @Description:
 */
public class LoginRunnable implements Runnable {

    private String userId;

    public LoginRunnable(String userId){
        this.userId = userId;
    }


    @Override
    public void run() {
        if("mysql".equals(PropertiesUtils.APP.getProperty("app.service"))){
            try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
                //初始化好友列表
                List<ChatFriendVo> list = new ArrayList<>();
                List<ChatFriend> friendList = new ArrayList<>();
                Criteria<ChatFriend, Long> friendCriteria = new Criteria<>(ChatFriend.class);
                friendCriteria.addFilter("userId=",userId);
                friendList = friendCriteria.findListByExample();

                Criteria<ChatUserInfo, Long> userInfoCriteria = new Criteria<>(ChatUserInfo.class);
                for(ChatFriend chatFriend : friendList){
                    userInfoCriteria.addFilter("userId=",chatFriend.getFriendUserId());
                    ChatUserInfo chatUserInfo = userInfoCriteria.findByExample();
                    ChatFriendVo chatFriendVo = ChatUtils.convertRedisFriendVo(userId, chatUserInfo);
                    list.add(chatFriendVo);
                }
                jedis.hset(ConstantsRedisKey.CHAT_USER_FRIEND, userId, JSONObject.toJSONString(list));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
