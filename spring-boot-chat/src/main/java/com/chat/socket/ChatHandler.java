package com.chat.socket;

import com.alibaba.fastjson.JSONObject;
import com.chat.enums.SocketCmd;
import com.chat.vo.ChatGroupMsgVo;
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

    public static String GROUP = "group";

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

            if(SocketCmd.CHAT_ENTER.getKey().equals(chatMsgVo.getCmd())) {
                webSocketSet.put(userId, session);
                addOnlineCount();
            }else if (SocketCmd.CHATTING.getKey().equals(chatMsgVo.getCmd())) {
                session.sendMessage(new TextMessage(JSONObject.toJSONString(chatMsgVo)));
                sendToUser(chatMsgVo);
            }else if (SocketCmd.CHAT_VIDEO.getKey().equals(chatMsgVo.getCmd())) {
                sendToUserVideoCall(chatMsgVo);
            }else if(SocketCmd.OUT_CHAT.getKey().equals(chatMsgVo.getCmd())){
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
        String userId = session.getAttributes().get(UID).toString();
        webSocketSet.remove(userId);
        subOnlineCount();
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("error");
        subOnlineCount();
    }

    public void sendToUser(ChatMsgVo chatMsg) {
        String toUserId = chatMsg.getToUserId();
        String fromUserId = chatMsg.getFromUserId();
        String sendMessage = chatMsg.getSendtext();
        //过滤输入法输入的表情
        sendMessage= EmojiFilter.filterEmoji(sendMessage);
        chatMsg.setSendtext(sendMessage);
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


    public void sendToUserVideoCall(ChatMsgVo chatMsg) {
        String toUserId = chatMsg.getToUserId();
        try {
            if (webSocketSet.get(toUserId) != null) {
                webSocketSet.get(toUserId).sendMessage(new TextMessage("video"));
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
        logger.info("当前在线人数："+ChatHandler.onlineCount);
    }


    public static synchronized void subOnlineCount() {
        ChatHandler.onlineCount--;
    }

}
