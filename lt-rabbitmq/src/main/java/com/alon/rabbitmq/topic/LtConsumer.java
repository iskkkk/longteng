package com.alon.rabbitmq.topic;

import com.alon.common.constant.RabbitConstant;
import com.alon.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @ClassName BaiduConsumer
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/15 11:11
 * @Version 1.0
 **/
public class LtConsumer {
    public static void main(String[] args) {
        Connection connection = RabbitUtils.getConnection();
        final Channel channel;
        try {
            channel = connection.createChannel();
            channel.queueDeclare(RabbitConstant.QUEUE_LT,true,false,false,null);
            //queueBind用于将队列与交换机绑定
            // 参数一：队列名
            //参数二：交换机名
            //参数三：路由key(暂时用不到)
            channel.queueBind(RabbitConstant.QUEUE_LT,RabbitConstant.EXCHANGES_WEATHER_TOPIC,"us.#");
            //解绑
            //channel.queueUnbind(RabbitConstant.QUEUE_LT,RabbitConstant.EXCHANGES_WEATHER_TOPIC,"us.#");

            channel.basicQos(1);
            channel.basicConsume(RabbitConstant.QUEUE_LT,false,new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("lt收到气象信息:" + new String(body) );
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
