package org.mics.lang.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

/**
 * ip信息工具
 * @author mics
 * @date 2020年5月22日
 * @version  1.0
 */
public class IpUtils {

    /**
     * 参数最大长度
     */
    private static final int IP_LENGTH = 15;

    /**
     * 逗号
     */
    private static final String COMMA = ",";

    /**
     * localhost
     */
    private static final String LOCAL_IP = "127.0.0.1";

    /**
     * IPv6
     */
    private static final String IPV6 = "0:0:0:0:0:0:0:1";

    /**
     * unknown
     */
    private static final String UNKNOWN = "unknown";

    /**
     * 获取客户端IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (LOCAL_IP.equals(ip)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > IP_LENGTH) {
            if (ip.indexOf(COMMA) > 0) {
                ip = ip.substring(0, ip.indexOf(COMMA));
            }
        }

        if (IPV6.equals(ip)) {
            ip = LOCAL_IP;
        }

        return ip;
    }
}
