package com.alon.impl.redis.key;

/**
 * @ClassName WxRedisKey
 * @Description 商品缓存key
 * @Author 一股清风
 * @Date 2019/5/17 15:37
 * @Version 1.0
 **/
public class WxRedisKey extends BasePrefix {

    private WxRedisKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static WxRedisKey baseInfo = new WxRedisKey(60 * 60 * 2, "baseInfo");
}
