package com.util.properties;

import java.util.Properties;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/16 14:06
 * @Description:
 */
public class PropertiesUtils {

    public static Properties APP = null;
    static {
        APP = new Properties();
        try {
            APP.load(PropertiesUtils.class.getClassLoader().getResourceAsStream("app.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
