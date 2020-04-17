package com.chat.enums;

/**
 * @Copyright © 北京互融时代软件有限公司
 * @Author: Jidn
 * @Date: 2020/4/15 16:47
 * @Description:
 */
public enum SocketCmd {

    /**
     *
     */
    CHAT_ENTER("enter_chatting","好友聊天进入聊天室"),
    CHATTING("chatting","好友开始聊天"),
    OUT_CHAT("out_chatting","退出聊天"),
    CHAT_GROUP_ENTER("enter_group_chat","进入群聊"),
    CHAT_GROUP("chat_group","发送群聊"),
    CHAT_VIDEO("chat_video","视频通话请求"),




    ;

    private String key;
    private String remark;
    SocketCmd(String key,String remark) {
        this.remark = remark;
        this.key = key;
    }
    public String getRemark() {
        return remark;
    }

    public String getKey(){
        return key;
    }
}
