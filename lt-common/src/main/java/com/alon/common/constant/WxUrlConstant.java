package com.alon.common.constant;

/**
 * @ClassName WxUrlConstant
 * @Description 微信接口地址
 * @Author 一股清风
 * @Date 2019/5/21 13:50
 * @Version 1.0
 **/
public class WxUrlConstant {

    public static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/";
    public static final String UNIONID_URL = "https://api.weixin.qq.com/cgi-bin/user/info?";
    public static final String USER_GRANT_URL = "https://api.weixin.qq.com/sns/oauth2/";
    // 获取账号粉丝列表
    public static final String GET_FANS_LIST = "https://api.weixin.qq.com/cgi-bin/user/";
    //统一下单
    public static final String UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";

}
