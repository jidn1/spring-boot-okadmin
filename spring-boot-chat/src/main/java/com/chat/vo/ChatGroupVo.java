package com.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Copyright © 北京互融时代软件有限公司
 * @Author: Jidn
 * @Date: 2020/4/15 15:38
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatGroupVo implements Serializable {

    private Long id;

    private String groupName;

    private String groupCreateUserId;

    private String groupHeadUrl;

}
