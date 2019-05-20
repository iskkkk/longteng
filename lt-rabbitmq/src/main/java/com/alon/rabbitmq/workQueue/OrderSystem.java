package com.alon.rabbitmq.workQueue;

import com.alibaba.fastjson.JSONObject;
import com.alon.common.constant.RabbitConstant;
import com.alon.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @ClassName OrderSystem
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/14 19:16
 * @Version 1.0
 **/
public class OrderSystem {
    public static void main(String[] args) {
        Connection connection = RabbitUtils.getConnection();
        Channel channel = null;
        try {
            channel = connection.createChannel();
            channel.queueDeclare(RabbitConstant.QUEUE_SMS,true,false,false,null);
            for (int i = 100; i < 200; i++) {
                Sms sms = new Sms("乘客" + i, "17666666666" + i, "预定成功");
                String jsonString = new JSONObject().toJSONString(sms);
                System.out.println(jsonString);
                channel.basicPublish("", RabbitConstant.QUEUE_SMS, null, jsonString.getBytes());
            }
            System.out.println("发送数据成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            RabbitUtils.closeConnection(connection,channel);
        }
    }
}
