package com.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class ChatMsgVo {
    private String fromUser;

    private String toUserid;

    private String msgtype;

    private String sendtext;

    private Integer msgStatus;

    private Date created;

    private Date modified;
}
