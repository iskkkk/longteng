package com.alon.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName RabbitUtils
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/14 18:50
 * @Version 1.0
 **/
public class RabbitUtils {
    //factory用于创建mq的物理连接
    private static ConnectionFactory factory = new ConnectionFactory();
    static {
        factory.setHost("192.168.16.128");
        factory.setPort(5672);
        factory.setUsername("alon");
        factory.setPassword("123456");
        factory.setVirtualHost("/test");
    }
    public static Connection getConnection() {
        Connection connection = null;
        try {
            //tcp物理链接
            connection = factory.newConnection();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
      * 方法表述: 关闭MQ连接
      * @Author 一股清风
      * @Date 11:08 2019/5/15
      * @param       connection
     * @param       channel
      * @return void
    */
    public static void closeConnection(Connection connection, Channel channel) {
        try {
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
