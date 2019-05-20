package com.alon.rabbitmq.pubsub;

import com.alon.common.constant.RabbitConstant;
import com.alon.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Scanner;

/**
 * @ClassName WeatherBureau
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/15 10:59
 * @Version 1.0
 **/
public class WeatherBureau {
    public static void main(String[] args) {
        Connection connection = RabbitUtils.getConnection();
        final Channel channel;
        Channel finalChannel = null;
        try {
            channel = connection.createChannel();
            String input = new Scanner(System.in).next();
            //第一个参数：交换机名称
            //第二个参数：队列名
            channel.basicPublish(RabbitConstant.EXCHANGES_WEATHER,"",null,input.getBytes());
            finalChannel = channel;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            RabbitUtils.closeConnection(connection,finalChannel);
        }
    }
}
