package com.oauth.dao;

import com.mvc.base.dao.BaseDao;
import com.oauth.model.SystemUser;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/17 17:25
 * @Description:
 */
public interface SystemUserDao extends BaseDao<SystemUser,Integer> {

    public SystemUser findByUserName(String username);
}
