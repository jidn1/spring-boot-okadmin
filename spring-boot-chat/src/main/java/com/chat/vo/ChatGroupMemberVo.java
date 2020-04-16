package com.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Copyright © 北京互融时代软件有限公司
 * @Author: Jidn
 * @Date: 2020/4/15 15:59
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatGroupMemberVo  implements Serializable {

    private String groupName;

    private String groupMemberUserId;

    private String groupMemberUserName;

}
