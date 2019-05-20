package com.alon.rabbitmq.topic;

import com.alon.common.constant.RabbitConstant;
import com.alon.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName WeatherBureau
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/15 10:59
 * @Version 1.0
 **/
public class WeatherBureau {
    public static void main(String[] args) {
        Map<String,String> area = new LinkedHashMap<String,String>();
        area.put("Chine.henan.xichuan.20190515","中国河南淅川20190515天气数据");
        area.put("Chine.hainan.haikou.20190515","中国海南海口20190515天气数据");
        area.put("Chine.guangdong.shenzhen.20190515","中国广东深圳20190515天气数据");
        area.put("us.cal.la.20190515","美国加州洛杉矶20190515天气数据");

        area.put("Chine.henan.xichuan.20190516","中国河南淅川20190516天气数据");
        area.put("Chine.hainan.haikou.20190516","中国海南海口20190516天气数据");
        area.put("Chine.guangdong.shenzhen.20190516","中国广东深圳20190516天气数据");
        area.put("us.cal.la.20190516","美国加州洛杉矶20190516天气数据");
        Connection connection = RabbitUtils.getConnection();
        final Channel channel;
        Channel finalChannel = null;
        try {
            channel = connection.createChannel();
            area.forEach((k,v) -> {
                //第一个参数：交换机名称
                //routing key:第二个参数相当于数据筛选的条件
                try {
                    channel.basicPublish(RabbitConstant.EXCHANGES_WEATHER_TOPIC,k,null,v.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            finalChannel = channel;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            RabbitUtils.closeConnection(connection,finalChannel);
        }
    }
}
