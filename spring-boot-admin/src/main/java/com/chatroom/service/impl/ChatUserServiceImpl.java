package com.chatroom.service.impl;

import com.chatroom.model.ChatUser;
import com.chatroom.service.ChatUserService;
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
 * <p> ChatUserService </p>
 * @author:  jidn
 * @Date :   2020-04-03 16:10:39 
 */
@Service("chatUserService")
public class ChatUserServiceImpl implements ChatUserService{
	

    @Override
    public PageResult findPageBySql(Map<String,String> param) {
        Map<String,Object> paraMap = new HashMap<>(3);
        Criteria<ChatUser,Integer> criteria = new Criteria<>(ChatUser.class);
        Page<ChatUser> page = PageFactory.getPage(param);
        criteria.findAll();
        return new PageResult(page,page.getTotal(), page.getPages(), page.getPageSize());
    }

}
