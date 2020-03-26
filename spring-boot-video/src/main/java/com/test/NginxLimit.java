package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CountDownLatch;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2020/1/3 13:36
 * @Description: Nginx 限流压测
 */
public class NginxLimit {

    //也可以使用AB压测 ab -n1000 -c 10 http://localhost:6003
    private static int count = 0;
    public static void main(String[] args) throws IOException, InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 80; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                        String result = NginxLimit.sendGet("http://localhost:6003/movie/list?page=0&limit=18");
                        System.out.println(result);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
        latch.countDown();
        Thread.sleep(5000);
        System.out.println(count);
    }



    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlName = url;
            URL realUrl = new URL(urlName);
            URLConnection conn = realUrl.openConnection();// 打开和URL之间的连接
            conn.setRequestProperty("accept", "*/*");// 设置通用的请求属性
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setConnectTimeout(4000);
            conn.connect();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            count ++;
            System.out.println("发送GET请求出现异常！" + e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                System.out.println("关闭流异常");
            }
        }
        return result;
    }
}
