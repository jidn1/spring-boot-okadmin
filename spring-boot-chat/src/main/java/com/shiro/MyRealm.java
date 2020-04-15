package com.shiro;

import com.chat.service.ChatUserService;
import com.chat.vo.ChatUserInfoVo;
import com.util.PropertiesUtils;
import com.util.SpringUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/19 13:47
 * @Description: shiro realm
 */
public class MyRealm extends AuthorizingRealm {

    private static Logger logger = Logger.getLogger(MyRealm.class);




    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        ChatUserService chatUserService = (ChatUserService) SpringUtil.getBean(PropertiesUtils.APP.getProperty("app.service"));
        ChatUserInfoVo chatUser = chatUserService.getChatUser(token.getUsername());

        logger.info("-----"+token.getUsername()+"---------"+chatUser.getUsername());

        if (token.getUsername().equals(chatUser.getUsername())) {

            return new SimpleAuthenticationInfo(chatUser.getUsername(),
                    chatUser.getPassword(),
                    ByteSource.Util.bytes(chatUser.getSalt()),
                    getName());

        } else {
            logger.error("认证失败");
            throw new AuthenticationException();
        }

    }
}
