package com.alon.common.utils;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class QrUtils {

    // 特殊字符处理
    public static String UrlEncode(String src) {
        String url = null;
        try {
            url =  URLEncoder.encode(src, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
}
