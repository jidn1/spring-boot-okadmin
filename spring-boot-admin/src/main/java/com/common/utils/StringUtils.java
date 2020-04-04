package com.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @Copyright © 北京互融时代软件有限公司
 * @Author: Jidn
 * @Date: 2020/4/3 17:08
 * @Description: String 字符串处理类
 */
public class StringUtils {

    public static void main(String[] args) {
        System.out.println(filterString("哈哈哈jdiejij《》#"));
    }



    public static String matchUserName(String str){
        if(str.length() <= 18){
            return filterString(str);
        }
        return null;
    }


    public static String filterString(String str) throws PatternSyntaxException {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return  m.replaceAll("").trim();
    }


}
