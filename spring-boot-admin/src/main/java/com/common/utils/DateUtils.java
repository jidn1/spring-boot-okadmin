package com.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Copyright © 北京互融时代软件有限公司
 * @Author: Jidn
 * @Date: 2020/4/3 17:20
 * @Description:
 */
public class DateUtils {


    /**
     * 计算几天后的日期
     * @param digital
     * @return
     */
    public static Date calcAddDigitalDaysLater(int digital){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, digital);// 增加n天
        String str = sdf.format(calendar.getTime()) + " 23:59:59";
        try {
            return sdf1.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
