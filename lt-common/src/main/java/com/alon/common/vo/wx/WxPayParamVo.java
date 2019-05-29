package com.alon.common.vo.wx;

import com.alon.common.utils.DateFormUtils;

/**
 * @ClassName WxPayParamVo
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/28 18:26
 * @Version 1.0
 **/
public class WxPayParamVo {
    /**
     * 商户注册具有支付权限的公众号成功后即可获得
     */
    public String appId;
    /**
     * 当前的时间
     */
    public String timeStamp = DateFormUtils.getNowTime();
    /**
     * 随机字符串，不长于32位
     */
    public String nonceStr;
    /**
     * 统一下单接口返回的prepay_id参数值，提交格式如：prepay_id=***
     */
    public String payPackage;
    /**
     * 签名类型，默认为MD5，支持HMAC-SHA256和MD5
     */
    public String signType = "MD5";
    /**
     * 签名
     */
    public String paySign;

    /**
     * 支付成功跳转页面
     */
    public String callbackUrl = "https://www.google.com.hk/";
}
