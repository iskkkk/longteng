package com.alon.rabbitmq;

import com.alon.common.constant.RabbitConstant;
import com.alon.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @ClassName Consumer
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/14 18:26
 * @Version 1.0
 **/
public class Consumer {
    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            //tcp物理链接
            connection = RabbitUtils.getConnection();
            //创建通信通道，相当于tcp中的虚拟链接
            channel = connection.createChannel();
            channel.queueDeclare(RabbitConstant.QUEUE_NAME,true,false,false,null);
            //创建一个消息消费者
            //第二个参数代表是否自动确认收到消息，false代表手动编程来确认消息，这是mq的推荐做法
            //第三个参数要传入DefaultConsumer的实现类
            channel.basicConsume(RabbitConstant.QUEUE_NAME,false,new Reciver(channel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
