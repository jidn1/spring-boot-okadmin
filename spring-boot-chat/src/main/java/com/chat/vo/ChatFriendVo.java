package com.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Copyright © 北京互融时代软件有限公司
 * @Author: Jidn
 * @Date: 2020/4/7 14:40
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatFriendVo implements Serializable {

    /**
     *
     */
    private Long id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 好友id
     */
    private String friendUserId;

    /**
     * 好友状态
     */
    private Integer status;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatarImg;
}
