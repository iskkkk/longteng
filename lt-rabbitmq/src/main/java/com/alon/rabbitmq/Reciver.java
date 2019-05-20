package com.alon.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @ClassName Reciver
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/14 18:30
 * @Version 1.0
 **/
@Slf4j
public class Reciver extends DefaultConsumer {

    private Channel channel;
    /**
      * 方法表述: 重写构造函数
      * @Author 一股清风
      * @Date 18:31 2019/5/14
      * @param       channel 通道对像需要从外层传入，再handleDelivery()中要用到
      * @return 
    */
    public Reciver(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    /**
      * 方法表述: 
      * @Author 一股清风
      * @Date 18:31 2019/5/14
      * @param       consumerTag
     * @param       envelope
     * @param       properties
     * @param       body
      * @return void
    */
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//        super.handleDelivery(consumerTag, envelope, properties, body);
        String message = new String(body);
        System.out.println("消费者接收到：" + message);
        //签收消息，也叫确认消息
        //envelope.getDeliveryTag()：获取这个消息的TagId
        //false：只确认签收当前的消息，设置为true则代表签收该消费者所有未签收的消息
        channel.basicAck(envelope.getDeliveryTag(),false);
    }
}
