package com.vz.paas.core.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 系统工具类，用于获取系统相关信息
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 11:22:51
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomSystemUtil {

    /**
     * 内网IP
     */
    private static String INTRANET_IP = getIntranetIp();

    /**
     * 外网IP
     */
    private static String INTERNET_IP = getInternetIp();

    /**
     * 获取内网IP
     * @return 内网IP
     */
    private static String getIntranetIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取外网IP
     * @return 外网IP
     */
    private static String getInternetIp() {
        try {
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            Enumeration<InetAddress> addrs;

            while (networks.hasMoreElements()) {
                addrs = networks.nextElement().getInetAddresses();
                while (addrs.hasMoreElements()) {
                    ip = addrs.nextElement();
                    if (ip instanceof Inet4Address && ip.isSiteLocalAddress() && !ip.getHostAddress().equals(INTRANET_IP)) {
                        return ip.getHostAddress();
                    }
                }
            }
            return INTERNET_IP;
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
}
