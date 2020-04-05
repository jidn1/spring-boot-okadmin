package com.chat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chat.common.constants.ConstantsRedisKey;
import com.chat.common.utils.JsonResult;
import com.chat.model.ChatUser;
import com.chat.service.ChatUserService;
import com.db.Criteria;
import com.github.pagehelper.Page;
import com.redis.BaseRedis;
import com.util.Md5Encrypt;
import com.util.PageFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> ChatUserService </p>
 *
 * @author: jidn
 * @Date :   2020-04-03 16:10:39
 */
@Service("chatUserService")
public class ChatUserServiceImpl implements ChatUserService {


    @Override
    public JsonResult login(String username, String password) {
        try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {

            String user = jedis.hget(ConstantsRedisKey.CHAT_USER, username);
            ChatUser chatUser = JSONObject.parseObject(user, ChatUser.class);

            String pass = Md5Encrypt.md5(chatUser.getPassword() + chatUser.getSalt());
            if(!pass.equals(password)){
                return new JsonResult().setMsg("login_password_error");
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
