package com.chatroom.service.impl;

import com.chatroom.model.ChatFriend;
import com.chatroom.service.ChatFriendService;
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
 * <p> ChatFriendService </p>
 * @author:  jidn
 * @Date :   2020-04-03 18:35:16 
 */
@Service("chatFriendService")
public class ChatFriendServiceImpl implements ChatFriendService{
	

    @Override
    public PageResult findPageBySql(Map<String,String> param) {
        Map<String,Object> paraMap = new HashMap<>(3);
        Criteria<ChatFriend,Integer> criteria = new Criteria<>(ChatFriend.class);
        Page<ChatFriend> page = PageFactory.getPage(param);
        criteria.findAll();
        return new PageResult(page,page.getTotal(), page.getPages(), page.getPageSize());
    }

}
