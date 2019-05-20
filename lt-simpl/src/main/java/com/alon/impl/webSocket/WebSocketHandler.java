package com.alon.impl.webSocket;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于处理我们的 websocket 具体请求的类
 * 和原生的区别,这个对象是单例的,所以除了公用的数据外,不能使用成员变量
 */
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    private static final Map<String,WebSocketSession> allClient=new ConcurrentHashMap<String, WebSocketSession>();//用于存储所有用户连接的 map
    /**
      * 方法表述: 当用户建立连接的时候执行,按照我们的需求,在此处,我们应有用户连接就保存
      * @Author 一股清风
      * @Date 13:06 2019/5/14
      * @param       session
      * @return void
    */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("用户建立连接了");
        //通过属性找到保存的 name
        Map<String, Object> attributes = session.getAttributes();//获取我们在拦截器中保存了参数的那个 map
        String name= (String) attributes.get(Config.attrName);
        //存连接
        allClient.put(name,session);
    }

    /**
      * 方法表述: 当收到用户发过来的消息的时候
      * @Author 一股清风
      * @Date 13:06 2019/5/14
      * @param       session 发送者的 session
     * @param       message
      * @return void
    */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
       //规定好,发过来的数据必须是 json 格式的数据, 其中有一个 key  toName 代表接收者的名字,还有一个 content 代表的真正发送的内容
        JSONObject json = JSONObject.parseObject(new String(message.asBytes()));
        String toName = json.getString("toName");//代表接收者
        String content = json.getString("content");//发送的内容
        WebSocketSession webSocketSession = allClient.get(toName);//获取到接收者的连接
        String fromName = (String) session.getAttributes().get(Config.attrName);//获取到发送者的名字
        String toMessage=" 收到来自:"+ fromName+"的消息:"+content;//拼接发出去的内容
        TextMessage textMessage=new TextMessage(toMessage);//将发送的内容
        sendMessage(webSocketSession,textMessage);

    }

    /**
      * 方法表述: 发送消息给指定的人
      * @Author 一股清风
      * @Date 13:07 2019/5/14
      * @param       session
     * @param       message
      * @return void
    */
    public static void sendMessage(WebSocketSession session,TextMessage message){
        if (session != null&&session.isOpen()) {
            try {
                session.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
      * 方法表述: 发送消息给所有人
      * @Author 一股清风
      * @Date 13:07 2019/5/14
      * @param       message
      * @return void
    */
    public  void sendToAll(TextMessage message){
        for (Map.Entry<String, WebSocketSession> entry : allClient.entrySet()) {
            WebSocketSession session = entry.getValue();
            if (session != null&&session.isOpen()) {
                try {
                    session.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
      * 方法表述: 当出现错误的时候
      * @Author 一股清风
      * @Date 13:07 2019/5/14
      * @param       session
     * @param       exception
      * @return void
    */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        String name = (String) session.getAttributes().get(Config.attrName);//找到当前关闭连接的保存的属性
        if (session.isOpen()) {
            session.close();
        }
        allClient.remove(name);//移除
    }

    /**
      * 方法表述: 连接关闭之后,此处应该移除保存的连接
      * @Author 一股清风
      * @Date 13:07 2019/5/14
      * @param       session
     * @param       status
      * @return void
    */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String name = (String) session.getAttributes().get(Config.attrName);//找到当前关闭连接的保存的属性
        allClient.remove(name);//从当前 map 中移除
    }

    /**
      * 方法表述: 发送消息（用于扫码支付回调自动跳转通知用到）
      * @Author 一股清风
      * @Date 13:08 2019/5/14
      * @param       orderNo
     * @param       message
      * @return void
    */
    public static void sendMessage(String orderNo,String message){
        WebSocketSession webSocketSession = allClient.get(orderNo);//获取到接收者的连接
        if (webSocketSession != null && webSocketSession.isOpen()
                && StringUtils.isNotBlank(message)) {
            try {
                TextMessage textMessage=new TextMessage(message);//将发送的内容
                webSocketSession.sendMessage(textMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
