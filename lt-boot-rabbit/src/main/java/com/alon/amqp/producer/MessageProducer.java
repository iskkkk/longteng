package com.alon.amqp.producer;

import com.alon.amqp.model.Employee;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName MessageProducer
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/16 13:05
 * @Version 1.0
 **/
@Component
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        //correlationData：消息的附加信息，即自定义id
        //isAck:代表消息是否被broker(MQ)接收。true代表接收，false代表拒收
        //cause:如果拒收，则说明拒收的原因，帮助后续处理
        @Override
        public void confirm(CorrelationData correlationData, boolean isAck, String cause) {
            System.out.println(correlationData);
            System.out.println("ack:" + isAck);
            if (!isAck) {
                System.out.println(cause);
            }
        }
    };

    public void sendMessage(Employee e) {
        //CorrelationData对象的作用是作为消息的附加信息传递，通常我们用它来保存消息的自定义id
        CorrelationData cd = new CorrelationData(e.getEmpno() + "-" + new Date().getTime());
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.convertAndSend("springboot-exchange","hr.employee",e,cd);
    }

    RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchangeName, String routingKey) {
            System.err.println("code:" + replyCode + ",text:" + replyText);
            System.err.println("exchange:" + exchangeName + ",routingKey:" + routingKey);
        }
    };
}
