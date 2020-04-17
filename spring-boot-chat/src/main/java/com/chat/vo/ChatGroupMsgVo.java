package com.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Copyright © 北京互融时代软件有限公司
 * @Author: Jidn
 * @Date: 2020/4/15 16:29
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatGroupMsgVo implements Serializable {

    private String cmd;

    private String token;

    private String groupName;

    private String fromUserId;

    private String fromUserName;

    private String sendtext;

    private Integer msgType;

    private Integer msgStatus;

    private Date created = new Date();

    private Date modified = new Date();
}
