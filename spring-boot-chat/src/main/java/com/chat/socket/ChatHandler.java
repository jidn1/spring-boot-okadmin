package com.chat.socket;

import com.alibaba.fastjson.JSONObject;
import com.chat.vo.ChatMsgVo;
import com.common.queue.ChatQueue;
import com.common.utils.EmojiFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Copyright © 北京互融时代软件有限公司
 * @Author: Jidn
 * @Date: 2020/4/8 13:35
 * @Description:
 */
@Component
@ServerEndpoint("/chatSocket")
public class ChatHandler extends TextWebSocketHandler {

    private final static Logger logger = LoggerFactory.getLogger(ChatHandler.class);


    public static String UID = "userId";

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
     */
    private static ConcurrentHashMap<String, WebSocketSession> webSocketSet = new ConcurrentHashMap<String, WebSocketSession>();


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String content = message.getPayload();
        if(!StringUtils.isEmpty(content)){
            ChatMsgVo chatMsgVo = JSONObject.parseObject(content, ChatMsgVo.class);
            String userId = session.getAttributes().get(UID).toString();

            if("enter_chatting".equals(chatMsgVo.getCmd())) {
                webSocketSet.put(userId, session);
                addOnlineCount();
            }else if ("chatting".equals(chatMsgVo.getCmd())) {
                session.sendMessage(new TextMessage(JSONObject.toJSONString(chatMsgVo)));
                sendToUser(chatMsgVo);
            }else if("out_chatting".equals(chatMsgVo.getCmd())){
                subOnlineCount();
            }

        }

    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    }


    /**
     * 当连接关闭时被调用
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("error");
    }

    public void sendToUser(ChatMsgVo chatMsg) {
        String toUserId = chatMsg.getToUserId();
        String fromUserId = chatMsg.getFromUserId();
        String sendMessage = chatMsg.getSendtext();
        //过滤输入法输入的表情
        sendMessage= EmojiFilter.filterEmoji(sendMessage);
        ChatQueue.produce(chatMsg);
        try {
            if (webSocketSet.get(toUserId) != null) {
                webSocketSet.get(toUserId).sendMessage(new TextMessage(JSONObject.toJSONString(chatMsg)));
            }else{
                webSocketSet.get(fromUserId).sendMessage(new TextMessage("当前用户不在线"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static synchronized int getOnlineCount() {
        return onlineCount;
    }


    public static synchronized void addOnlineCount() {
        ChatHandler.onlineCount++;
    }


    public static synchronized void subOnlineCount() {
        ChatHandler.onlineCount--;
    }

}
