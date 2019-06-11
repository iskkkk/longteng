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

    public static final String NATIVE = "NATIVE";
    public static final String JSPAY = "JSAPI";
    public static final String SUCCESS = "SUCCESS";

    //发送模板消息
    public static final String SEND_TEMPLE = "https://api.weixin.qq.com/cgi-bin/message/template/";
    /*==============================代金券API列表==============================*/
    //条件查询批次列表API
    public static final String SELECT_BATCH = "https://api.mch.weixin.qq.com/mmpaymkttransfers/query_coupon_stock";

    public static final String WX_CARD_ACTIVITY_CREATE_URL = "https://api.weixin.qq.com/card/mkt/activity/create?access_token=";



}
