package com.alon.amqp.consumer;

import com.alon.amqp.model.Employee;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassName MessageConsumer
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/16 16:20
 * @Version 1.0
 **/
@Component
public class MessageConsumer {
    /**
      * 方法表述: 用于接收消息的方法
      * @Author 一股清风
      * @Date 16:26 2019/5/16
      * @param
      * @return void
    */
    @RabbitHandler //通知springboot下面的方法用于接收消息，
    // 这个方法运行后将处于等待的状态，有新的消息进来就会自动触发下面的方法处理消息
    //@RabbitListener用于声明式定义消息接收的队列与exchange绑定的信息
    //在springboot中，消费者使用注解获取消息
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "springboot-queue",durable = "true"),
                    exchange = @Exchange(value = "springboot-exchange",durable = "true",type = "topic"),
                    key = "#"
            )
    )
    //@Payload代表运行时将消息反序列化后注入到后面的参数中
    public void handleMessage(@Payload Employee employee, Channel channel,
                                @Headers Map<Object,Object> headers) {
        System.out.println("=========================");
        System.out.println("接收到：" + employee.getEmpno() + ":" + employee.getName());
        //所有消息处理后，必须进行消息的ack,channel.basicAck()进行确认接收
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            channel.basicAck(tag,false);
            System.out.println("=========================");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
