package com.chatroom.service.impl;

import com.chatroom.dao.ChatMessageDao;
import com.chatroom.model.ChatMessage;
import com.chatroom.service.ChatMessageService;
import com.db.Criteria;
import javax.annotation.Resource;

import com.movie.model.Movie;
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
	

    @Resource
    private ChatMessageDao chatMessageDao;

    @Override
    public PageResult findPageBySql(Map<String,String> param) {
        Map<String,Object> paraMap = new HashMap<>(3);
        Page<ChatMessage> page = PageFactory.getPage(param);
        List<ChatMessage> list = chatMessageDao.findPageBySql(paraMap);
        return new PageResult(list,page.getTotal(), page.getPages(), page.getPageSize());
    }

}
