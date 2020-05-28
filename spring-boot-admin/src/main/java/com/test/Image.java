package com.test;



import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.DataInputStream;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2020/1/7 10:57
 * @Description:
 */
public class Image {


    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        //照片URL
        String imageUrl = "https://i.52112.com/icon/jpg/256/20180718/18064/1267743.jpg";
        URL url = new URL(imageUrl);
        //打开网络输入流
        DataInputStream dis = new DataInputStream(url.openStream());
        String newImageName = "D://ifeng/1.jpg";
        //建立一个新的文件
        FileOutputStream fos = new FileOutputStream(new File(newImageName));
        byte[] buffer = new byte[1024];
        int length;
        //开始填充数据
        while ((length = dis.read(buffer)) > 0) {
            fos.write(buffer, 0, length);
        }
        dis.close();
        fos.close();
    }


}
