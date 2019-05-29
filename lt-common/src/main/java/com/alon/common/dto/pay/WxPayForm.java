package com.alon.common.dto.pay;

import com.alon.common.utils.NumberUtil;

/**
 * @ClassName WxPayForm
 * @Description 微信支付参数
 * @Author 一股清风
 * @Date 2019/5/13 14:00
 * @version 1.0
 **/
public class WxPayForm {

    /**
     * 平台id
     */
    public String appId;
    /**
     * 商户id
     */
    public String mchId;
    /**
     * 平台密钥
     */
    public String key;
    public String tradeType;
    /**
     * 获取发起电脑 ip
     */
    public String spbillCreateIp = "127.0.0.1";
    public String notifyUrl = "http://aloning.imwork.net/pay/notify";
    /**
     * 商品id
     */
    public String productId;
    /**
     * 上传此参数no_credit--可限制用户不能使用信用卡支付
     */
    public String limitPay;
    public String openId = "onhAr1eEQ8QUJroJ3fiIgixLPW24";
    /**
     * Y，传入Y时，支付成功消息和支付详情页将出现开票入口。
     * 需要在微信支付商户平台或微信公众平台开通电子发票功能，传此字段才可生效
     */
    public String receipt = "N";
    /**
     * 商品名称
     */
    public String body = "123test";
    public String orderNo;
    public String nonceStr = NumberUtil.getRandomString(6);
    /**
     * 支付成功跳转页面
     */
    public String callbackUrl;
}
