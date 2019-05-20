package com.alon.impl.redis.key;

/**
 * @ClassName UserKey
 * @Description 用户缓存key
 * @Author 一股清风
 * @Date 2019/5/17 15:37
 * @Version 1.0
 **/
public class UserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600*24 *2;//默认两天

    /**
     * 防止被外面实例化
     */
    private UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 需要缓存的字段
     */
    public static UserKey token = new UserKey(TOKEN_EXPIRE,"token");
    public static UserKey getById = new UserKey(0, "id");

}
