package com.chat.group;

import com.chat.socket.ChatSocketShake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @Copyright © 北京互融时代软件有限公司
 * @Author: Jidn
 * @Date: 2020/4/16 9:52
 * @Description:
 */
@Configuration("chatGroupSocketConfig")
@EnableWebSocket
public class ChatGroupSocketConfig implements WebSocketConfigurer {

    public static final Logger log = LoggerFactory.getLogger(ChatGroupSocketConfig.class);

    @Bean("chatGroupHandler")
    public ChatGroupHandler chatGroupSocketHandler() {
        return new ChatGroupHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        log.debug("websocket handler 注册");
        registry.addHandler(chatGroupSocketHandler(), "/chatGroupSocket").addInterceptors(new ChatGroupSocketShake()).setAllowedOrigins("*");
        registry.addHandler(chatGroupSocketHandler(), "/chatGroupSocket").addInterceptors(new ChatGroupSocketShake()).withSockJS();
    }
}
