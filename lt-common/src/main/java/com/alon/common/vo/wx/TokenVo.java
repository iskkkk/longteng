package com.alon.common.vo.wx;

/**
 * @ClassName TokenVo
 * @Description 用于转换redis缓存中的值
 * @Author 一股清风
 * @Date 2019/5/21 13:55
 * @Version 1.0
 **/
public class TokenVo {

    public String token;//access_token值

    public Integer expiresIn;//过期时间

    public Integer errcode;//错误编码

    public String errmsg;//错误信息

}
