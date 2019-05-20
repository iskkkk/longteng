package com.alon.amqp.seckill;

import com.alon.amqp.config.MQConfig;
import com.alon.impl.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName MQSender
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/17 15:54
 * @Version 1.0
 **/
@Service
@Slf4j
public class MQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*public void send(Object message){
        log.info("send message:" + message);
        rabbitTemplate.convertAndSend(MQConfig.QUEUE, message);
    }*/

    public void sendTopic(Object message) {
        String msg = RedisUtil.beanToString(message);
        log.info("send topic message:"+msg);
        rabbitTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", msg+"1");
        rabbitTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", msg+"2");
    }

    public void sendSeckillMessage(SeckillMessage message){
        String msg = RedisUtil.beanToString(message);
        log.info("send message:"+msg);
        rabbitTemplate.convertAndSend(MQConfig.QUEUE, msg);

    }
}
