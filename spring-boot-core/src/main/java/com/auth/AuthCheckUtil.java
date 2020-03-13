package com.auth;

import com.util.Md5Encrypt;
import com.util.PropertiesUtils;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/16 14:21
 * @Description:
 */
public class AuthCheckUtil {

    /**
     * 加盐
     */
    public static final String salt = "BkrUg0j1";

    public static String checkAuth() {
        try {
            boolean ifPass = false;
            String oauthIp = PropertiesUtils.APP.getProperty("app.oauthIp");
            String oauthKey = PropertiesUtils.APP.getProperty("app.oauthKey");
            String localIp = MacUtil.getAllLocalIp();

            String[] ips = localIp.split(",");

            for (String p : ips) {
                if (oauthIp.equals(p)) {
                    localIp = p;
                    break;
                }
            }

            if (!StringUtils.isEmpty(oauthIp) && localIp.equals(oauthIp)) {
                String localMac = MacUtil.getLocalMac(oauthIp);
                String oauthKeyNew = Md5Encrypt.md5(localMac + ", " + oauthIp + ", " + salt);
                ifPass = ifPass(oauthKey, oauthKeyNew);

                if (ifPass) {
                    success();
                }
            }

            if (!ifPass) {
                fail();
                System.exit(-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    static SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    public static boolean ifPass(String oldOauthKey, String newOauthKey) {
        if (oldOauthKey.equals(newOauthKey)) {
            return true;
        }
        return false;
    }


    public static void success() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("**********************************************").append("\n");
        stringBuilder.append("***************WWW.ZJJTV.TOP******************").append("\n");
        stringBuilder.append("【success=true】【Successful authorization【Authorized】】").append("\n");
        stringBuilder.append("********©2018-2022 All Rights Reserved********").append("\n");
        stringBuilder.append("**********************************************").append("\n");

        System.out.println("" + stringBuilder.toString());
    }


    public static void fail() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("**********************************************").append("\n");
        stringBuilder.append("***************WWW.ZJJTV.TOP******************").append("\n");
        stringBuilder.append("【success=false】【Failure authorization【Unauthorized】】").append("\n");
        stringBuilder.append("********©2018-2022 All Rights Reserved********").append("\n");
        stringBuilder.append("**********************************************").append("\n");

        System.out.println("" + stringBuilder.toString());
    }


}
