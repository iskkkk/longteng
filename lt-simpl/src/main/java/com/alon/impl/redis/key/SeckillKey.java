package com.alon.impl.redis.key;

/**
 * @ClassName SeckillKey
 * @Description 秒杀缓存key
 * @Author 一股清风
 * @Date 2019/5/17 15:37
 * @Version 1.0
 **/
public class SeckillKey extends BasePrefix {
    private SeckillKey(String prefix) {
        super(prefix);
    }

    public static SeckillKey isGoodsOver = new SeckillKey("go");
}
