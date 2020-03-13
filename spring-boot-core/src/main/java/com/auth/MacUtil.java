package com.auth;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Scanner;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/16 14:05
 * @Description: 获取mac 地址
 */
public class MacUtil {


    /**
     * @param args
     * @throws UnknownHostException
     * @throws SocketException
     */
    public static void main(String[] args) {
        System.out.println("--------------------------------------");
        System.out.println("huronginfo:" + System.getProperty("user.name"));
        System.out.println("huronginfo:" + System.getProperty("java.version"));
        System.out.println("huronginfo:" + System.getProperty("os.name"));
        System.out.println("huronginfo:" + System.getProperty("os.arch"));
        System.out.println("huronginfo:" + System.getProperty("os.version"));
        System.out.println("huronginfo:" + System.getProperty("user.home"));
        System.out.println("huronginfo:" + getAllLocalIp());
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------------------------------------");

        System.out.println("huronginfo-CN:请输入IP,获取mac地址");
        System.out.println("huronginfo-EN:please enter IP,get mac address");
        String next = scanner.next();
        System.out.println("huronginfo-mac=" + getLocalMac(next));
    }


    public static String getAllLocalIp() {
        InetAddress inetAddress = null;
        boolean bFindIP = false;
        Enumeration<NetworkInterface> netInterfaces = null;
        StringBuffer iparr = new StringBuffer();
        try {
            netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (netInterfaces.hasMoreElements()) {
            if (bFindIP) {
                break;
            }
            NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
            // ----------特定情况，可以考虑用ni.getName判断
            // 遍历所有ip
            Enumeration<InetAddress> ips = ni.getInetAddresses();
            while (ips.hasMoreElements()) {
                inetAddress = (InetAddress) ips.nextElement();
                if (inetAddress.isSiteLocalAddress() && !inetAddress.isLoopbackAddress() // 127.开头的都是lookback地址
                        && inetAddress.getHostAddress().indexOf(":") == -1) {
                    iparr.append(inetAddress.getHostAddress()).append(",");
                }
            }
        }
        return iparr.toString();
    }

    public static String getLocalMac(String ip) {
        if (ip == null || "".equals(ip)) {
            return null;
        }
        InetAddress inetAddress = null;
        boolean bFindIP = false;
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (netInterfaces.hasMoreElements()) {
            if (bFindIP) {
                break;
            }
            NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
            // ----------特定情况，可以考虑用ni.getName判断
            // 遍历所有ip
            Enumeration<InetAddress> ips = ni.getInetAddresses();
            while (ips.hasMoreElements()) {
                inetAddress = (InetAddress) ips.nextElement();
                if (inetAddress.isSiteLocalAddress() && !inetAddress.isLoopbackAddress() // 127.开头的都是lookback地址
                        && inetAddress.getHostAddress().indexOf(":") == -1) {
                    String ipstr = inetAddress.getHostAddress();
                    if (ip.equals(ipstr)) {
                        bFindIP = true;
                        break;
                    }
                }
            }
        }


        try {
            //获取网卡，获取地址
            byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append(":");
                }
                //字节转换为整数
                int temp = mac[i] & 0xff;
                String str = Integer.toHexString(temp);
                if (str.length() == 1) {
                    sb.append("0" + str);
                } else {
                    sb.append(str);
                }
            }
            return sb.toString().toUpperCase();
        } catch (Exception e) {
        }
        return null;
    }
}
