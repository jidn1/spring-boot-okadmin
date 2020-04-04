package com.chatroom.service.impl;

import com.chatroom.dao.ChatUserDao;
import com.chatroom.dao.ChatUserInfoDao;
import com.chatroom.model.ChatUser;
import com.chatroom.model.ChatUserInfo;
import com.chatroom.service.ChatUserInfoService;
import com.common.model.JsonResult;
import com.common.utils.DateUtils;
import com.db.Criteria;
import javax.annotation.Resource;

import com.movie.model.Movie;
import com.util.uuid.UUIDUtil;
import org.springframework.stereotype.Service;
import com.common.model.PageResult;
import com.util.PageFactory;
import com.github.pagehelper.Page;
import org.thymeleaf.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> ChatUserInfoService </p>
 * @author:  jidn
 * @Date :   2020-04-03 16:11:00
 */
@Service("chatUserInfoService")
public class ChatUserInfoServiceImpl implements ChatUserInfoService{

    @Resource
    private ChatUserInfoDao chatUserInfoDao;
    @Resource
    private ChatUserDao chatUserDao;

    @Override
    public PageResult findPageBySql(Map<String,String> param) {
        Map<String,Object> paraMap = new HashMap<>(3);
        Page<ChatUserInfo> page = PageFactory.getPage(param);

        String ifdelete = param.get("ifdelete");
        String username = param.get("username");

        if(!StringUtils.isEmpty(ifdelete)){
            paraMap.put("ifdelete",ifdelete);
        }
        if(!StringUtils.isEmpty(username)){
            paraMap.put("username","%"+username+"%");
        }

        List<ChatUserInfo> list = chatUserInfoDao.findPageBySql(paraMap);
        return new PageResult(list,page.getTotal(), page.getPages(), page.getPageSize());
    }

    @Override
    public JsonResult addChatUser(Map<String, String> param) {

        try {
            //获取盐值
            String salt = UUIDUtil.getUUID();
            String userId = UUIDUtil.getUUID();
            String username = param.get("username");
            String password = param.get("password");
            String nickName = param.get("nickName");
            String avatarImg = param.get("avatarImg");
            String signature = param.get("signature");
            String gender = param.get("gender");
            String expiration = param.get("expiration");

            Criteria<ChatUser,Integer> criteria = new Criteria<>(ChatUser.class);
            criteria.addFilter("username=",username);
            ChatUser chatUser1 = criteria.findByExample();
            if(chatUser1 != null){
                return new JsonResult().setSuccess(false).setMsg("user_already_exists");
            }

            String matchUserName = com.common.utils.StringUtils.matchUserName(username);
            if(matchUserName == null){
                return new JsonResult().setSuccess(false).setMsg("username_do_not_match");
            }
            ChatUser chatUser = new ChatUser();
            chatUser.setUserId(userId);
            chatUser.setUsername(matchUserName);
            chatUser.setPassword(password);
            chatUser.setSalt(salt);
            chatUser.setCreated(new Date());
            chatUser.setModified(new Date());
            chatUserDao.insert(chatUser);

            ChatUserInfo chatUserInfo = new ChatUserInfo();
            chatUserInfo.setUserid(userId);
            chatUserInfo.setNickName(nickName);
            chatUserInfo.setAvatarImg(avatarImg);
            chatUserInfo.setSignature(signature);
            chatUserInfo.setGender(Integer.valueOf(gender));
            chatUserInfo.setExpirationTime(DateUtils.calcAddDigitalDaysLater(Integer.valueOf(expiration)));
            chatUserInfo.setCreated(new Date());
            chatUserInfo.setModified(new Date());
            chatUserInfoDao.insert(chatUserInfo);
            return new JsonResult().setSuccess(true).setMsg("success");
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
