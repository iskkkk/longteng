package com.alon.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.alon")
@MapperScan("com.alon.mapper.dao")
@EnableScheduling //开启定时任务
//@EnableAsync //开启异步任务
public class LtWebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LtWebApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(LtWebApplication.class);
    }
}
