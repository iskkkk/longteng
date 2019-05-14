package com.alon.impl.webSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 用于定义请求地址的类,设置如何处理消息,实现指定接口后, sring 就知道这是一个 websocket 的配置类
 */
@Configuration//声明当前类是一个配置文件类
@EnableWebSocket//启用 websocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        //第一个参数 handler 指的就是我们收到消息等操作的处理器,也就是有连接的时候怎么办,有消息的时候怎么办
        //拦截器参数,主要是用于在请求到达处理器之前需要做什么的,比如我们需要在到达处理器之前区分出当前连接是谁
            webSocketHandlerRegistry.addHandler(new WebSocketHandler(),"/webSocket/*")
                    .addInterceptors(new WebSocketInterceptor()).setAllowedOrigins("*");
    }

}
