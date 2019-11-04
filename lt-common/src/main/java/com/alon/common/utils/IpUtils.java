package com.alon.common.utils;


import org.apache.commons.lang3.text.StrTokenizer;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * @ClassName IpUtils
 * @Description 获取ip地址
 * Nginx部分：
 *
 * location ~ ^/static {
 * proxy_pass ....;
 * proxy_set_header X-Forward-For $remote_addr ;
 * }
 * @Author zoujiulong
 * @Date 2019/11/1 14:05
 * @Version 1.0
 **/
public class IpUtils {
    public static final String _255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    public static final Pattern pattern = Pattern.compile("^(?:"+_255+"\\.){3}" +_255+"$");

    public static String longToIpV4(long longIp) {
        int octet3 = (int) ((longIp >> 24) % 256);
        int octet2 = (int) ((longIp >> 16) % 256);
        int octet1 = (int) ((longIp >> 8) % 256);
        int octet0 = (int) ((longIp) % 256);
        return octet3 + "." + octet2 + "." + octet1 + "." + octet0;
    }

    public static long ipV4ToLong(String ip) {
        String[] octets = ip.split("\\.");
        return (Long.parseLong(octets[0]) << 24) + (Integer.parseInt(octets[1]) << 16)
                + (Integer.parseInt(octets[2]) << 8) + Integer.parseInt(octets[3]);
    }

    public static boolean isIPV4Private(String ip) {
        long longIP = ipV4ToLong(ip);
        return (longIP >= ipV4ToLong("10.0.0.0") && longIP <= ipV4ToLong("10.255.255.255"))
                || (longIP >= ipV4ToLong("172.16.0.0") && longIP <= ipV4ToLong("172.31.255.255"))
                || longIP >= ipV4ToLong("192.168.0.0") && longIP <= ipV4ToLong("192.168.255.255");
    }

    public static boolean isIPV4Valid(String ip) {
        return pattern.matcher(ip).matches();
    }

    public static String getIpForRequest(HttpServletRequest request) {
        String ip;
        boolean found = false;
        if ((ip = request.getHeader("x-forwarded-for")) != null) {
            StrTokenizer tokenizer = new StrTokenizer(ip,",");
            while (tokenizer.hasNext()) {
                ip = tokenizer.nextToken().trim();
                if (isIPV4Valid(ip) && isIPV4Private(ip)) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
