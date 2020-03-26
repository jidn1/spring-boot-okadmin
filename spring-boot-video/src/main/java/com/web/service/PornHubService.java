package com.web.service;

import com.common.model.PageResult;

import java.util.Map;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2020/1/2 19:11
 * @Description:
 */
public interface PornHubService {

    PageResult findPageBySql(Map<String,String> param);
}
