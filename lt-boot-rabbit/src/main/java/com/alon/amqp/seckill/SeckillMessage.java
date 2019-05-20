package com.alon.amqp.seckill;

import com.alon.model.seckill.LtUser;
import lombok.Data;

/**
 * @ClassName SeckillMessage
 * @Description 消息体
 * @Author 一股清风
 * @Date 2019/5/17 15:45
 * @Version 1.0
 **/
@Data
public class SeckillMessage {
    private LtUser user;
    private long goodsId;
}
