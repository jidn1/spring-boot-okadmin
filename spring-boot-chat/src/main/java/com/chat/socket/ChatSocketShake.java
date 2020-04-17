package com.chat.socket;

import com.chat.vo.ChatUserInfoVo;
import com.common.utils.ChatUtils;
import com.redis.BaseRedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Copyright © 北京互融时代软件有限公司
 * @Author: Jidn
 * @Date: 2020/4/8 13:34
 * @Description:
 */
public class ChatSocketShake implements HandshakeInterceptor {

    public static final Logger log = LoggerFactory.getLogger(ChatSocketShake.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> sessionMap) throws Exception {
        log.info("webSocket连接成功");
        try {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpServletRequest httpServletRequest = servletRequest.getServletRequest();
            try (Jedis jedis = BaseRedis.JEDIS_POOL.getResource()) {
                ChatUserInfoVo session = ChatUtils.getSession(jedis, httpServletRequest);
                String groupName = httpServletRequest.getParameter(ChatUtils.GROUP_NAME);
                if (null != session) {
                    sessionMap.put(ChatHandler.UID, session.getUserid());
                    if (!StringUtils.isEmpty(groupName)) {
                        sessionMap.put(ChatHandler.GROUP, session.getGroupName());
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
