package com.alon.rabbitmq.workQueue;

import com.alon.common.constant.RabbitConstant;
import com.alon.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @ClassName SmsSender
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/15 10:11
 * @Version 1.0
 **/
public class SmsSender {
    public static void main(String[] args) {
        Connection connection = RabbitUtils.getConnection();
        Channel channel = null;
        try {
            channel = connection.createChannel();
            channel.queueDeclare(RabbitConstant.QUEUE_SMS,true,false,false,null);
            Channel finalChannel = channel;
            //如果不写basicQos(1)，MQ会将所有请求平均发送给所有消费者
            //basicQos,MQ不再对消费者一次发送多个请求，而是消费者处理完一个消息确认后，再从队列中获取新的
            finalChannel.basicQos(1);//处理完一个取一个
            finalChannel.basicConsume(RabbitConstant.QUEUE_SMS,false,new DefaultConsumer(finalChannel){
                /**
                  * 方法表述: 消息的处理者
                  * @Author 一股清风
                  * @Date 10:16 2019/5/15
                  * @param       consumerTag
                 * @param       envelope
                 * @param       properties
                 * @param       body
                  * @return void
                */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String jsonSms = new String(body);
                    System.out.println("SMSSender-短信发送成功：" + jsonSms);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finalChannel.basicAck(envelope.getDeliveryTag(),false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
