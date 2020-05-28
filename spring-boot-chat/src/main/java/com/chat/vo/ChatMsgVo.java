package com.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Copyright: 正经吉
 * @author: ChatMsgVo
 * @version: V1.0
 * @Date: 2020/4/4
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatMsgVo implements Serializable {

    private String cmd;

    private String token;

    private String fromUserId;

    private String toUserId;

    private String sendtext;

    private Integer msgType;

    private Integer msgStatus;

    private String sdp;

    private String type;

    private IceCandidate iceCandidate;

    private Date created = new Date();

    private Date modified = new Date();
}
