package com.alon.springExchange.exchange;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * @ClassName NewsProducer
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/15 17:17
 * @Version 1.0
 **/
public class NewsProducer {
    private RabbitTemplate rabbitTemplate = null;

    public RabbitTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNews(String routingKey,News news) {
        //向exchange发送数据
        //第一个参数：routingkey
        //第二个参数：news 创建的对象,可以使字符串，byte[]或者任何实现了【序列化接口的对象】
        rabbitTemplate.convertAndSend(routingKey,news);
        System.out.println("发送成功");

    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        NewsProducer newsProducer = (NewsProducer) context.getBean("newsProducer");
        newsProducer.sendNews("us.20190515",new News("新华社","特朗普",new Date(),"国际新闻"));
        newsProducer.sendNews("China.20190515",new News("凤凰TV","Alon V5",new Date(),"国际新闻"));
    }
}
