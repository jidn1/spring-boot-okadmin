package com.hothub.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/27 14:35
 * @Description:
 */
@Data
public class TopHotVo implements Serializable {

    private String zhTitle;

    private String zhUrl;

    private String hpTitle;

    private String hpUrl;

    private String blogTitle;

    private String blogUrl;
}
