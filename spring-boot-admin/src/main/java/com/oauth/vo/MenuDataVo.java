package com.oauth.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2020/1/7 15:39
 * @Description:
 */
@Data
public class MenuDataVo implements Serializable {


    private String title;

    private String href;

    private String icon;

    private Boolean spread = false;

    private String pkey;

    private String mkey;

    private String resourceType;

    private List<MenuDataVo> children;
}
