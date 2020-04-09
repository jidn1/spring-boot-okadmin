package com.chat.socket;

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
 * @Date: 2020/4/8 13:33
 * @Description:
 */
@Configuration("chatWebSocketConfig")
@EnableWebSocket
public class ChatWebSocketConfig implements WebSocketConfigurer {
    public static final Logger log = LoggerFactory.getLogger(ChatWebSocketConfig.class);

    @Bean("chatHandler")
    public ChatHandler chatSocketHandler() {
        return new ChatHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        log.debug("websocket handler 注册");
        registry.addHandler(chatSocketHandler(), "/chatSocket").addInterceptors(new ChatSocketShake()).setAllowedOrigins("*");
        registry.addHandler(chatSocketHandler(), "/chatSocket").addInterceptors(new ChatSocketShake()).withSockJS();
    }
}
