package com.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Copyright © 北京互融时代软件有限公司
 * @Author: Jidn
 * @Date: 2020/4/7 12:04
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatUserInfoVo implements Serializable {

    /**
     *
     */
    private Long id = 0L;

    /**
     *
     */
    private String userid;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatarImg;

    /**
     * 签名
     */
    private String signature;

    /**
     * 性别 0 女 1男  2 未知
     */
    private Integer gender;

    /**
     * 过期时间
     */
    private Date expirationTime;

    /**
     * 是否删除 0 未 1 已
     */
    private Integer ifdelete;

    private String username;


    private String password;

    private String salt;


    @Override
    public String toString() {
        return "ChatUserInfoVo{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatarImg='" + avatarImg + '\'' +
                ", signature='" + signature + '\'' +
                ", gender=" + gender +
                ", expirationTime=" + expirationTime +
                ", ifdelete=" + ifdelete +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
