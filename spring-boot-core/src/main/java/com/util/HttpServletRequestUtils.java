package com.util;

import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/21 17:29
 * @Description:
 */
public class HttpServletRequestUtils {

    public static Map<String,String> getParams(HttpServletRequest request){
        Map<String,String> map = new HashMap<String,String>();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = StringUtils.trimAllWhitespace(request.getParameter(name));
            if (!StringUtils.isEmpty(value)) {
                if("limit".equals(name)){//防止恶意攻击,最多查100条
                    if(Integer.valueOf(value)>100){
                        map.put(name, "100");
                    }else if(Integer.valueOf(value)==0){
                        map.put(name, "10");
                    }else{
                        map.put(name, value);
                    }
                }else{
                    map.put(name, value);
                }
            }
        }
        return map;
    }

}
