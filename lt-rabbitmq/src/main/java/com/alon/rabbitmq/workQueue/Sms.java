package com.alon.rabbitmq.workQueue;

import lombok.Data;

/**
 * @ClassName Sms
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/14 19:22
 * @Version 1.0
 **/
@Data
public class Sms {
    private String name;
    private String mobile;
    private String content;

    public Sms(String name, String mobile, String content) {
        this.name = name;
        this.mobile = mobile;
        this.content = content;
    }
}
