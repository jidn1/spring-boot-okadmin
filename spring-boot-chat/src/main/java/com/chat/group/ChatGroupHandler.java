package com.chat.group;

import com.alibaba.fastjson.JSONObject;
import com.chat.enums.SocketCmd;
import com.chat.socket.ChatHandler;
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
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Copyright © 北京互融时代软件有限公司
 * @Author: Jidn
 * @Date: 2020/4/16 9:53
 * @Description:
 */
@Component
@ServerEndpoint("/chatGroupSocket")
public class ChatGroupHandler extends TextWebSocketHandler {

    private final static Logger logger = LoggerFactory.getLogger(ChatHandler.class);

    public static String UID = "userId";

    public static String GROUP = "group";

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    /**
     * 群聊
     */
    private static ConcurrentHashMap<String, HashMap<String,WebSocketSession>> chatSocketGroup = new ConcurrentHashMap<String, HashMap<String,WebSocketSession>>();


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String content = message.getPayload();
        if(!StringUtils.isEmpty(content)){
            ChatGroupMsgVo chatGroupMsgVo = JSONObject.parseObject(content, ChatGroupMsgVo.class);
            String userId = session.getAttributes().get(UID).toString();

            if(SocketCmd.CHAT_GROUP_ENTER.getKey().equals(chatGroupMsgVo.getCmd())){
                if(session.getAttributes().get(GROUP) != null){
                    String groupName = session.getAttributes().get(GROUP).toString();
                    HashMap<String,WebSocketSession> map = new HashMap<>();
                    map.put(userId,session);
                    chatSocketGroup.put(groupName,map);
                }
            }else if(SocketCmd.CHAT_GROUP.getKey().equals(chatGroupMsgVo.getCmd())){
                ChatGroupMsgVo chatMsgVo = JSONObject.parseObject(content, ChatGroupMsgVo.class);
                session.sendMessage(new TextMessage(JSONObject.toJSONString(chatMsgVo)));
                sendGroup(chatMsgVo);
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
        subOnlineCount();
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("error");
        subOnlineCount();
    }


    public void sendGroup(ChatGroupMsgVo chatMsg) {
        String sendMessage = chatMsg.getSendtext();
        String groupName = chatMsg.getGroupName();
        //过滤输入法输入的表情
        sendMessage= EmojiFilter.filterEmoji(sendMessage);
        chatMsg.setSendtext(sendMessage);
        //ChatQueue.produce(chatMsg);
        try {
            HashMap<String, WebSocketSession> sessionHashMap = chatSocketGroup.get(groupName);
            for (String userId : sessionHashMap.keySet()){
                sessionHashMap.get(userId).sendMessage(new TextMessage(JSONObject.toJSONString(chatMsg)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static synchronized int getOnlineCount() {
        return onlineCount;
    }


    public static synchronized void addOnlineCount() {
        ChatGroupHandler.onlineCount++;
        logger.info("当前在线人数："+ChatGroupHandler.onlineCount);
    }


    public static synchronized void subOnlineCount() {
        ChatGroupHandler.onlineCount--;
    }


}
