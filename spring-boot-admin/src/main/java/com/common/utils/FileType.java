package com.common.utils;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/24 15:43
 * @Description:
 */
public class FileType {

    private static Logger logger = Logger.getLogger(FileType.class);

    public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

    private FileType() {
    }

    static {
        getAllFileType(); // 初始化文件类型信息
    }

    /**
     * [getAllFileType,常见文件头信息]
     */
    private static void getAllFileType() {
        FILE_TYPE_MAP.put("FFD8FF", "jpg"); // JPEG (jpg)
        FILE_TYPE_MAP.put("89504E47", "png"); // PNG (png)
        FILE_TYPE_MAP.put("47494638", "gif"); // GIF (gif)
        FILE_TYPE_MAP.put("424D", "bmp"); // 16色位图(bmp)
        FILE_TYPE_MAP.put("0000010001002020 ", "ico"); // 16色位图(bmp)

    }


    public static String getFileType(InputStream is) {
        String res = null;
        try {
            byte[] b = new byte[10];
            is.read(b, 0, b.length);
            String fileCode = bytesToHexString(b);

            logger.error(fileCode);

            // 这种方法在字典的头代码不够位数的时候可以用但是速度相对慢一点
            Iterator<String> keyIter = FILE_TYPE_MAP.keySet().iterator();
            while (keyIter.hasNext()) {
                String key = keyIter.next();
                if (key.toLowerCase().startsWith(fileCode.toLowerCase()) || fileCode.toLowerCase().startsWith(key.toLowerCase())) {
                    res = FILE_TYPE_MAP.get(key);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 得到上传文件的文件头
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


}
