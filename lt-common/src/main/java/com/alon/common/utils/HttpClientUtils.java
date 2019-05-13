package com.alon.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class HttpClientUtils {
    public static String post(String url, String xml) {
        System.out.println("===============开始请求=============");
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        HttpEntity eitity;
        try {
            eitity = new ByteArrayEntity(xml.getBytes("utf-8"), ContentType.TEXT_XML);
            httppost.setEntity(eitity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String content = EntityUtils.toString(entity, "utf-8");
                    return content;
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            System.out.println("发送post请求异常：" + e);
            return e.getMessage();
        } catch (UnsupportedEncodingException e) {
            System.out.println("发送post请求异常："+ e);
            return e.getMessage();
        } catch (IOException e) {
            System.out.println("发送post请求异常：" + e);
            return e.getMessage();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                System.out.println("关闭post请求异常：" + e);
            }
        }
        return ("发送post请求失败！");
    }

    public static InputStream String2Inputstream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }
}
