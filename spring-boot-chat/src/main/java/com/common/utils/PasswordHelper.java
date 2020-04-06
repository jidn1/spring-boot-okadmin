package com.common.utils;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/19 19:12
 * @Description:
 */
public class PasswordHelper {

    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private static String algorithmName = "md5";
    private static int hashIterations = 2;


    /**
     * 加密任何字符串
     * @param pwd
     * @param salt
     * @return
     */
    public String encryString(String pwd, String salt) {
        String newPassword = new SimpleHash(algorithmName, pwd, ByteSource.Util.bytes(salt), hashIterations).toHex();
        return newPassword;
    }

    /**
     * 加密密码，返回盐值和密码
     *
     * @param password
     * @return [0] 盐值，[1]密码
     */
    public static String[] encryptPassword(String password) {
        String salt = randomNumberGenerator.nextBytes().toHex();
        String newPassword = new SimpleHash(algorithmName, password, ByteSource.Util.bytes(salt), hashIterations).toHex();
        return new String[] { salt, newPassword };

    }


}
