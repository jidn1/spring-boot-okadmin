package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/18 14:29
 * @Description:
 */
public class DateUtils {

    public static String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        return dateFormat.format(new Date());
    }
}
