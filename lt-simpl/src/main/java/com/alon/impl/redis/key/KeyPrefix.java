package com.alon.impl.redis.key;

/**
  * 方法表述: 缓冲key前缀
  * @Author 一股清风
  * @Date 11:45 2019/5/20
*/
public interface KeyPrefix {

    /**
     * 有效期
     * @return
     */
    public int expireSeconds();

    /**
     * 前缀
     * @return
     */
    public String getPrefix();
}
