package com.alon.impl.redis.key;

/**
 * @ClassName GoodsKey
 * @Description 商品缓存key
 * @Author 一股清风
 * @Date 2019/5/17 15:37
 * @Version 1.0
 **/
public class GoodsKey extends BasePrefix {

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60, "gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");
    public static GoodsKey getGoodsStock = new GoodsKey(0, "gs");
}
