package com.chatroom.service.impl;

import com.chatroom.model.ChatMessage;
import com.chatroom.service.ChatMessageService;
import com.db.Criteria;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.common.model.PageResult;
import com.util.PageFactory;
import com.github.pagehelper.Page;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> ChatMessageService </p>
 * @author:  jidn
 * @Date :   2020-04-03 18:34:52 
 */
@Service("chatMessageService")
public class ChatMessageServiceImpl implements ChatMessageService{
	

    @Override
    public PageResult findPageBySql(Map<String,String> param) {
        Map<String,Object> paraMap = new HashMap<>(3);
        Criteria<ChatMessage,Integer> criteria = new Criteria<>(ChatMessage.class);
        Page<ChatMessage> page = PageFactory.getPage(param);
        criteria.findAll();
        return new PageResult(page,page.getTotal(), page.getPages(), page.getPageSize());
    }

}
