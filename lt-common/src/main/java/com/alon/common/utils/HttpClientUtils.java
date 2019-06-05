package com.alon.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Map;

@Slf4j
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

    /**
      * 方法表述: get请求
      * @Author 一股清风
      * @Date 13:57 2019/5/21
      * @param       url
     * @param       map
      * @return java.util.Map<java.lang.String,java.lang.Object>
    */
    public static Map<String,Object> doGet(String url, Map<String,Object> map){
//        Map<String,Object> map = new HashMap<String,Object>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)//建立链接时间
                .setConnectionRequestTimeout(5000)//请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//允许自动重定向
                .build();
        //创建uri
        try{
            URIBuilder builder = new URIBuilder(url);
            if (map != null){
                for (String key: map.keySet()){
                    builder.addParameter(key, (String) map.get(key));
                }
            }
            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setConfig(requestConfig);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                String jsonResult = EntityUtils.toString(httpResponse.getEntity());
                //这里需要借助json工具类把jsonResult转换为map
//                map = new Gson().fromJson(jsonResult,map.getClass());
                map = JSON.parseObject(jsonResult,map.getClass());
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                httpClient.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
      * 方法表述: 通过url获取信息
      * @Author 一股清风
      * @Date 17:29 2019/5/21
      * @param       url
      * @return com.alibaba.fastjson.JSONObject
    */
    public static JSONObject getJsonObject(String url) {
        JSONObject jsonObject = null;
        HttpGet httpGet = null;
        try {
            //创建CloseableHttpClient类
            HttpClient httpClient =  HttpClientBuilder.create().build();
            //通过get方式提交
            httpGet =new  HttpGet(url);
            //execute发送请求
            HttpResponse execute = httpClient.execute(httpGet);
            //返回结果集
            HttpEntity entity=execute.getEntity();
            //返回的结果不为空
            if(entity!=null){
                //转换为String
                String string = EntityUtils.toString(entity,"UTF-8");
                //转换为json格式
                jsonObject= JSONObject.parseObject(string);
            }
        } catch (ClientProtocolException e) {
            log.info("请求异常信息：" + e.getMessage());
        } catch (IOException e) {
            log.info("转换异常信息：" + e.getMessage());
        } finally {
            //关闭链接
            httpGet.releaseConnection();
        }
        return  jsonObject;
    }

    /**
      * 方法表述: post请求（用于请求json格式的参数）
      * @Author 一股清风
      * @Date 18:46 2019/5/29
      * @param       url
     * @param       params
      * @return java.lang.String
    */
    public static String doPost(String url, String params){

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);// 创建httpPost     
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;

        try {
            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == 200) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                return jsonString;
            }
            else{
                log.error("请求返回:"+state+"("+url+")");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
