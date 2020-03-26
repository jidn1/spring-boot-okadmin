package com.oauth.shiro;

import com.common.constants.ConstantsRedisKey;
import com.oauth.model.SystemUser;
import com.oauth.service.SystemMenuService;
import com.oauth.service.SystemUserService;
import com.util.Md5Encrypt;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
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

    @Resource
    private SystemUserService systemUserService;
    @Resource
    private SystemMenuService systemMenuService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SystemUser userInfo = (SystemUser) principals.getPrimaryPrincipal();

        Set<String> findPermissions = systemMenuService.findPermissions(userInfo.getUsername());
        authorizationInfo.setStringPermissions(findPermissions);

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        SystemUser userInfo = systemUserService.findByUserName(token.getUsername());

        logger.info("-----"+token.getUsername()+"---------"+userInfo.getUsername());

        if (token.getUsername().equals(userInfo.getUsername())) {

            return new SimpleAuthenticationInfo(userInfo.getUsername(),
                    userInfo.getPassword(),
                    ByteSource.Util.bytes(userInfo.getSalt()),
                    getName());

        } else {
            logger.error("认证失败");
            throw new AuthenticationException();
        }

    }
}
