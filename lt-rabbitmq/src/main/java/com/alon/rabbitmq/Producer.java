package com.alon.rabbitmq;

import com.alon.common.constant.RabbitConstant;
import com.alon.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Producer
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/14 17:54
 * @Version 1.0
 **/
@Slf4j
public class Producer {
    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            //tcp物理链接
            connection = RabbitUtils.getConnection();
            //创建通信通道，相当于tcp中的虚拟链接
            channel = connection.createChannel();
            //创建队列，声明创建一个队列，如果过队列已存在，则使用这个队列
            //第一个参数：队列名称ID；第二个参数：是否持久化，false对应不持久化数据，mq停掉数据就会丢失；
            //第三个参数：是否队列私有化，false代表所有消费者都可以访问，true代表只有第一个拥有它的消费者才能一直使用，
            //其他消费者不让访问
            //第四个参数：是否自动删除,false代表链接停掉后不自动删除这个队列
            //第五个参数：其他额外参数，null
            channel.queueDeclare(RabbitConstant.QUEUE_NAME,true,false,false,null);
            //四个参数
            //exchange，交换机，暂时用不到，在后面进行发布订阅时才会用到
            //队列名称
            //额外的设置属性
            //传递数据的字节(最后一个参数是要传递的消息字节数组)
            String message = "helloWorld!";
            channel.basicPublish("",RabbitConstant.QUEUE_NAME,null,message.getBytes());
            channel.close();
            connection.close();
            System.out.println("数据发送成功！");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
