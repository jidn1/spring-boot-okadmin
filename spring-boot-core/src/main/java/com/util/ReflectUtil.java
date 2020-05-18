package com.util;

import org.nutz.lang.Mirror;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/18 11:51
 * @Description:
 */
public class ReflectUtil {


    public static void save(Object t) {
        Mirror<?> mirror = Mirror.me(t.getClass());
        try {
            //如果创建时间存在就不能再次修改
            mirror.setValue(t, "created", getFormatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
            mirror.setValue(t, "modified", getFormatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            System.out.println("not find created column");
        }
    }

    public static void update(Object t) {
        Mirror<?> mirror = Mirror.me(t.getClass());
        try {
            //如果创建时间存在就不能再次修改
            mirror.setValue(t, "modified", getFormatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            System.out.println("not find created column");
        }
    }


    public final static String getFormatDateTime(Date date, String dateFormat) {

        SimpleDateFormat sf = null;
        try {
            sf = new SimpleDateFormat(dateFormat);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        return sf.format(date);
    }

}
