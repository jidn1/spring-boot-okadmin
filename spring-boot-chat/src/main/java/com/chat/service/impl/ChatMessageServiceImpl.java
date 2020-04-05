package com.chat.service.impl;

import com.chat.model.ChatMessage;
import com.chat.service.ChatMessageService;
import com.chat.vo.ChatMsgVo;
import com.db.Criteria;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @Copyright: 正经吉
 * @author: ChatMessageServiceImpl
 * @version: V1.0
 * @Date: 2020/4/5
 */
@Service("chatMessageService")
public class ChatMessageServiceImpl implements ChatMessageService {




    @Override
    public void save(ChatMsgVo chatMsgVo) {
        try {
            ChatMessage chatMessage = new ChatMessage();
            BeanUtils.copyProperties(chatMsgVo, chatMessage);
            Criteria<ChatMessage,Integer> criteria = new Criteria<>(ChatMessage.class);
            criteria.save(chatMessage);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
