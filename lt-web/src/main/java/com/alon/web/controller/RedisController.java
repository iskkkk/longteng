package com.alon.web.controller;

import com.alon.common.result.CodeMessage;
import com.alon.common.result.ResultData;
import com.alon.impl.redis.util.RedisConstants;
import com.alon.impl.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RedisController
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/20 11:34
 * @Version 1.0
 **/
@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisController {
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/set")
    public ResultData test(){
        try {
            redisUtil.set("redisTemplate","这是一条测试数据", RedisConstants.datebase2);
            String value = redisUtil.get("redisTemplate",RedisConstants.datebase2).toString();
            log.info("redisValue="+value);
            log.info("读取redis成功");
            return ResultData.success(value);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.error(CodeMessage.SERVER_ERROR);
        }
    }
}
