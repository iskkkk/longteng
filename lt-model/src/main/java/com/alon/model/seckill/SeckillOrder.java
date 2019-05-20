package com.alon.model.seckill;

import lombok.Data;

/**
 * @ClassName SeckillOrder
 * @Description 秒杀订单
 * @Author 一股清风
 * @Date 2019/5/17 14:56
 * @Version 1.0
 **/
@Data
public class SeckillOrder {
    private Long id;
    private Long userId;
    private Long  orderId;
    private Long goodsId;
}
