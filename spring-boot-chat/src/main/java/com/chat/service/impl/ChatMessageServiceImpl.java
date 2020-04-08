package com.chat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chat.dao.ChatMessageDao;
import com.chat.model.ChatMessage;
import com.chat.service.ChatMessageService;
import com.chat.vo.ChatMsgVo;
import com.common.constants.ConstantsRedisKey;
import com.db.Criteria;
import com.redis.BaseRedis;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright: 正经吉
 * @author: ChatMessageServiceImpl
 * @version: V1.0
 * @Date: 2020/4/5
 */
@Service("chatMessageService")
public class ChatMessageServiceImpl implements ChatMessageService {


    @Resource
    private ChatMessageDao chatMessageDao;

    @Override
    public void save(ChatMsgVo chatMsgVo) {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
            ChatMessage chatMessage = new ChatMessage();
            BeanUtils.copyProperties(chatMsgVo, chatMessage);
            Criteria<ChatMessage,Integer> criteria = new Criteria<>(ChatMessage.class);
            criteria.save(chatMessage);

            System.out.println("chatMessage==="+JSONObject.toJSONString(chatMessage));
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
}
