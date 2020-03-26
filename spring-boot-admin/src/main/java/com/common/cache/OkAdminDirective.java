package com.common.cache;

import org.springframework.stereotype.Component;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/30 16:03
 * @Description:
 */
@Component
public class OkAdminDirective {

    public static String picture(String url){
        if(url.contains("http")){
            return url;
        }
        return "http://hry-exchange-public.oss-cn-beijing.aliyuncs.com/"+url;
    }
}
