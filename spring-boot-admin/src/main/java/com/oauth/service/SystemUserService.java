package com.oauth.service;

import com.common.model.JsonResult;
import com.oauth.model.SystemUser;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/17 17:00
 * @Description:
 */
public interface SystemUserService {

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    public SystemUser findByUserName(String username);

    /**
     * 启动初始化redis数据 桌面
     */
    public void initRedis();

    /**
     * 修改密码
     * @param oldPwd
     * @param newPwd
     * @param confirmPwd
     * @return
     */
    public JsonResult setPassword(String oldPwd,String newPwd,String confirmPwd);
}
