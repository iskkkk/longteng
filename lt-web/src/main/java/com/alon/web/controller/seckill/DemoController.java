package com.alon.web.controller.seckill;

import com.alon.amqp.seckill.MQSender;
import com.alon.impl.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DemoController
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/20 15:57
 * @Version 1.0
 **/
@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MQSender sender;

   /* @PostMapping("/mqSend")
    public ResultData<String> mq() {
        sender.send("{\n" +
                "\"user\" : \"\",\n" +
                "  \"goodsId\" : 1\n" +
                "}");
        return ResultData.success("Hello，world");
    }*/


}
