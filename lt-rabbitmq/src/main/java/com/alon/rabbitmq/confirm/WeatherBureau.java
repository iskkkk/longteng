package com.alon.rabbitmq.confirm;

import com.alon.common.constant.RabbitConstant;
import com.alon.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.*;

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
            //开启confirm监听
            channel.confirmSelect();
            channel.addConfirmListener(new ConfirmListener() {
                //第二个参数代表：接收的数据是否批量接受，一般用不到
                @Override
                public void handleAck(long l, boolean b) throws IOException {
                    System.out.println("消息已被Broket接收，Tag:" + l);
                }

                @Override
                public void handleNack(long l, boolean b) throws IOException {
                    System.out.println("消息已被Broket拒收，Tag:" + l);
                }
            });
            channel.addReturnListener(new ReturnCallback() {
                @Override
                public void handle(Return r) {
                    System.err.println("========================");
                    System.err.println("错误编码：" + r.getReplyCode() + "-错误描述" + r.getReplyText());
                    System.err.println("交换机：" + r.getExchange() + "-路由key" + r.getRoutingKey());
                    System.err.println("消息主题： " + new String(r.getBody()));
                    System.err.println("========================");
                }
            });
            area.forEach((k,v) -> {
                //第一个参数：交换机名称
                //routing key:第二个参数相当于数据筛选的条件
                //第三个参数：madatory true表示如果消息无法正常投递则return回生产者，如果false，则直接将消息放弃
                try {
                    channel.basicPublish(RabbitConstant.EXCHANGES_WEATHER_TOPIC,k,true,null,v.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            finalChannel = channel;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            RabbitUtils.closeConnection(connection,finalChannel);
        }
    }
}
