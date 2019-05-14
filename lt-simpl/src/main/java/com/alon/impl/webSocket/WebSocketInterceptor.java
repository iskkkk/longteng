package com.alon.impl.webSocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * Created by jackiechan on 2018/3/9/下午8:14
 * 用于拦截我们的请求
 */
public class WebSocketInterceptor extends HttpSessionHandshakeInterceptor {
    /**
      * 方法表述: 在我们的握手之前执行该方法,继续握手的话返回 true, 中断握手返回 fasle
      * @Author 一股清风
      * @Date 13:10 2019/5/14
      * @param       request
     * @param       response
     * @param       wsHandler
     * @param       attributes 用于给我们的连接设置属性的,设置的属性可以通过连接对象获取到,参数是多例对象,每个请求都会传递过来
      * @return boolean
    */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.err.println("握手之前");
        //得知道用户传过来的名字是什么
            //按照我们预先定义好的规则,用户的名字是请求参数的最后一个参数
        String url = request.getURI().toString();//请求的地址
        String name = url.substring(url.lastIndexOf("/") + 1);//获取到用户传递的 name

        //讲用户的名字添加到属性中

        attributes.put(Config.attrName,name);
        //如果此处需要判断登录,等校验操作,可以决定返回 true 或者 false
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        System.err.println("握手之后");

        super.afterHandshake(request, response, wsHandler, ex);
    }
}
