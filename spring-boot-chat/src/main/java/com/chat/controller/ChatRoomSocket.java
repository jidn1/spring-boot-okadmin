package com.chat.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.queue.ChatQueue;
import com.common.utils.EmojiFilter;
import com.chat.vo.ChatMsgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Copyright: 正经吉
 * @author: ChatRoomSocket
 * @version: V1.0
 * @Date: 2020/4/4
 *
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * @ServerEndpoint 可以把当前类变成websocket服务类
 */

@Controller
@ServerEndpoint(value = "/websocket/{userno}")
public class ChatRoomSocket {

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
     */
    private static ConcurrentHashMap<String, ChatRoomSocket> webSocketSet = new ConcurrentHashMap<String, ChatRoomSocket>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session WebSocketsession;
    /**
     * 当前发消息的人员编号
     */
    private String userno = "";


    /**
     * 连接建立成功调用的方法
     *
     * session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value = "userno") String userno, Session WebSocketsession) {
        userno = userno;
        this.WebSocketsession = WebSocketsession;
        webSocketSet.put(userno, this);
        addOnlineCount();
        //System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (!userno.equals("")) {
            webSocketSet.remove(userno);
            subOnlineCount();
            //System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        }
    }


    /**
     * 收到客户端消息后调用的方法
     *
     * @param chatMsg 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String chatMsg, Session session) {
        ChatMsgVo chatMsgVo = JSONObject.parseObject(chatMsg, ChatMsgVo.class);
        sendToUser(chatMsgVo);
        //sendAll(message);
    }


    /**
     * 给指定的人发送消息
     *
     * @param chatMsg 消息对象
     */
    public void sendToUser(ChatMsgVo chatMsg) {
        String toUserId = chatMsg.getToUserId();
        String sendMessage = chatMsg.getSendtext();
        //过滤输入法输入的表情
        sendMessage= EmojiFilter.filterEmoji(sendMessage);
        ChatQueue.produce(chatMsg);
        try {
            if (webSocketSet.get(toUserId) != null) {
                webSocketSet.get(toUserId).sendMessage(userno+"|"+sendMessage);
            }else{
                webSocketSet.get(userno).sendMessage("0"+"|"+"当前用户不在线");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给所有人发消息
     *
     * @param message
     */
    private void sendAll(String message) {
        String sendMessage = message.split("[|]")[1];
        //遍历HashMap
        for (String key : webSocketSet.keySet()) {
            try {
                //判断接收用户是否是当前发消息的用户
                if (!userno.equals(key)) {
                    webSocketSet.get(key).sendMessage(sendMessage);
                    System.out.println("key = " + key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }


    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.WebSocketsession.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }


    public static synchronized void addOnlineCount() {
        ChatRoomSocket.onlineCount++;
    }


    public static synchronized void subOnlineCount() {
        ChatRoomSocket.onlineCount--;
    }

}
