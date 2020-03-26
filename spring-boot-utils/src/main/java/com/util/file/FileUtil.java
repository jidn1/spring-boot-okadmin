package com.util.file;


/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/24 15:54
 * @Description:
 */
public class FileUtil {

    /**
     * 创建文件生成路径
     * String[0]  相对路径
     * String[1]  绝对路径
     * @param fileName
     * @return
     */
    public static String[] createFileSavePath(String fileName) {
        //根据文件名哈希2级打散文件路径
        int hashcode = fileName.hashCode();
        String dir1 = Integer.toHexString(hashcode & 0xf);
        String dir2 = Integer.toHexString(hashcode >>> 4 & 0xf);
        String hashPath = "okadmin";
        //设置hash路径为web访问路径
        String[] arr = new String[2];
        arr[0] = hashPath + "/" + fileName;//webpath 相对路径
        System.out.println(arr[0]);
        return arr;
    }


}
