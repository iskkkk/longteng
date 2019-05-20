package com.alon.springExchange.exchange;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @ClassName RabbitAdminTestor
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/15 19:10
 * @Version 1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class RabbitAdminTestor {
    @Resource(name = "rabbitAdmin")
    private RabbitAdmin rabbitAdmin;
    @Resource(name = "template")
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testCreateExchange() {
        rabbitAdmin.declareExchange(new FanoutExchange(
                "test.exchange.fanout",true,false));
        rabbitAdmin.declareExchange(new DirectExchange(
                "test.exchange.direct",true,false));
        rabbitAdmin.declareExchange(new TopicExchange(
                "test.exchange.topic",true,false));

    }

    @Test
    public void testQueueAndBind() {
        rabbitAdmin.declareQueue(new Queue("test.queue"));
        rabbitAdmin.declareBinding(new Binding(
                "test.queue",Binding.DestinationType.QUEUE,"test.exchange.topic",
                "#",new HashMap<>()
        ));
        rabbitTemplate.convertAndSend("test.exchange.topic","hello","11233255");
    }

    @Test
    public void testDelete() {
        rabbitAdmin.deleteQueue("test.queue");
        rabbitAdmin.deleteExchange("test.exchange.topic");
        rabbitAdmin.deleteExchange("test.exchange.fanout");
        rabbitAdmin.deleteExchange("test.exchange.direct");
    }
}
