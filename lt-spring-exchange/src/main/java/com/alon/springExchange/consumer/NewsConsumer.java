package com.alon.springExchange.consumer;

import com.alon.springExchange.exchange.News;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName Consumer
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/15 18:17
 * @Version 1.0
 **/
public class NewsConsumer {

    public void recv(News news) {
        System.out.println("接受到最新新闻：" + news.getTitle() + ":" + news.getSource());

    }

    public static void main(String[] args) {
        //初始化ioc容器
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext-consumer.xml");
    }
}
