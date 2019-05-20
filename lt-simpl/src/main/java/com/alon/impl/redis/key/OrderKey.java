package com.alon.impl.redis.key;

/**
 * @ClassName OrderKey
 * @Description 订单缓存key
 * @Author 一股清风
 * @Date 2019/5/17 15:37
 * @Version 1.0
 **/
public class OrderKey extends BasePrefix {

    public OrderKey(String prefix) {
        super(prefix);
    }
    public static OrderKey getSeckillOrderByUidGid = new OrderKey("seckill");
}
