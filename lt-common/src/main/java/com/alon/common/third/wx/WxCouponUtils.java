package com.alon.common.third.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alon.common.config.LtConfigParams;
import com.alon.common.constant.CardBgColorEnum;
import com.alon.common.constant.DateFormatterConstant;
import com.alon.common.constant.WXPayConstants;
import com.alon.common.constant.WxUrlConstant;
import com.alon.common.utils.DateFormUtils;
import com.alon.common.utils.HttpClientUtils;
import com.alon.common.utils.SignUtils;
import com.alon.common.utils.XmlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName WxCouponUtils
 * @Description 支付优惠券
 * @Author zoujiulong
 * @Date 2019/6/11 10:57
 * @Version 1.0
 **/
@Component
public class WxCouponUtils {

    @Autowired
    private LtConfigParams params;

    @Autowired
    private WxTokenUtil tokenUtil;

    /**
      * 方法表述: 根据批次查询代金券
      * @Author zoujiulong
      * @Date 17:39 2019/6/11
      * @param
      * @return void
    */
    public void searchForBatch() {
        String mchId = params.mchId;
        String appId = params.appId;
        String url = WxUrlConstant.SELECT_BATCH;
        Map<String,String> map = new TreeMap<>();
        map.put("coupon_stock_id","9497121");
        map.put("appid",appId);
        map.put("mch_id",mchId);
        map.put("nonce_str","1417574675");//随机字符串
        String sign = SignUtils.sign(params.key,map);
        map.put("sign",sign);
        String xml = XmlUtils.toXml(map, true);
        System.out.println("請求參數：" + xml);

        String resXml = HttpClientUtils.post(WxUrlConstant.SELECT_BATCH, xml);
        map = XmlUtils.doXMLParse(resXml);
        System.out.println("=============" + map);
    }

    /**
      * 方法表述: 创建支付后领取立减金活动接口
     * 通过此接口创建立减金活动。
     *将已创建的代金券cardid、跳转小程序appid、发起支付的商户号等信息通过此接口创建立减金活动，成功返回活动id即为创建成功。
     * 接口地址：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=21515658940X5pIn
      * @Author zoujiulong
      * @Date 18:33 2019/6/11
      * @param       begin_time 活动开始时间，精确到秒
     * @param       end_time 活动结束时间，精确到秒
     * @param       gift_num 单个礼包社交立减金数量（3-15个）
     * @param       max_partic_times_act 每个用户活动期间最大领取次数,最大为50，默认为1
     * @param       max_partic_times_one_day 每个用户活动期间单日最大领取次数,最大为50，默认为1
     * @param       card_id 卡券ID
     * @param       min_amt 最少支付金额，单位是元
     * @param       membership_appid 奖品指定的会员卡appid。如用户标签有选择商户会员，则需要填写会员卡appid，该appid需要跟所有发放商户号有绑定关系。
     * @param       new_tinyapp_user 可以指定为是否小程序新用户（membership_appid为空、new_tinyapp_user为false时，指定为所有用户）
      * @return com.alibaba.fastjson.JSONObject
    */
    public JSONObject createCardActivity(String begin_time, String end_time, int gift_num, int max_partic_times_act,
                                         int max_partic_times_one_day, String card_id, String min_amt,
                                         String membership_appid, boolean new_tinyapp_user) {
        try {
            // 创建活动接口之前的验证
            String msg = checkCardActivity(begin_time, end_time, gift_num, max_partic_times_act, max_partic_times_one_day, min_amt);
            if (null != msg) {
                JSONObject resultJson = new JSONObject(2);
                resultJson.put("errcode", "1");
                resultJson.put("errmsg", msg);
                return resultJson;
            }

            // 获取[商户名称]公众号的 access_token
            String accessToken = "";//tokenUtil.getAccessToken();

            // 调用接口传入参数
            JSONObject paramJson = new JSONObject(1);

            // info 包含 basic_info、card_info_list、custom_info
            JSONObject info = new JSONObject(3);

            // 基础信息对象
            JSONObject basic_info = new JSONObject(8);
            // activity_bg_color	是	活动封面的背景颜色，可参考：选取卡券背景颜色
            basic_info.put("activity_bg_color", CardBgColorEnum.COLOR_090.getBgName());
            // activity_tinyappid	是	用户点击链接后可静默添加到列表的小程序appid；
            basic_info.put("activity_tinyappid", WXPayConstants.APP_ID);
            // mch_code	是	支付商户号
            basic_info.put("mch_code", WXPayConstants.MCH_ID);
            // begin_time	是	活动开始时间，精确到秒（unix时间戳）
            basic_info.put("begin_time", DateFormUtils.getTenTimeByDate(begin_time));
            // end_time	是	活动结束时间，精确到秒（unix时间戳）
            basic_info.put("end_time", DateFormUtils.getTenTimeByDate(end_time));
            // gift_num	是	单个礼包社交立减金数量（3-15个）
            basic_info.put("gift_num", gift_num);
            // max_partic_times_act	否	每个用户活动期间最大领取次数,最大为50，不填默认为1
            basic_info.put("max_partic_times_act", max_partic_times_act);
            // max_partic_times_one_day	否	每个用户活动期间单日最大领取次数,最大为50，不填默认为1
            basic_info.put("max_partic_times_one_day", max_partic_times_one_day);

            // card_info_list	是	可以配置两种发放规则：小程序新老用户、新老会员
            JSONArray card_info_list = new JSONArray(1);
            JSONObject card_info = new JSONObject(3);
            // card_id	是	卡券ID
            card_info.put("card_id", card_id);
            // min_amt	是	最少支付金额，单位是分
            card_info.put("min_amt", String.valueOf(new BigDecimal(min_amt).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue()));
            /*
             * membership_appid	是	奖品指定的会员卡appid。如用户标签有选择商户会员，则需要填写会员卡appid，该appid需要跟所有发放商户号有绑定关系。
             * new_tinyapp_user	是	可以指定为是否小程序新用户
             * total_user	是	可以指定为所有用户
             * membership_appid、new_tinyapp_user、total_user以上字段3选1，未选择请勿填，不必故意填写false
             */
            if (StringUtils.isNotBlank(membership_appid)) {
                card_info.put("membership_appid", membership_appid);
            } else {
                if (new_tinyapp_user) {
                    card_info.put("new_tinyapp_user", true);
                } else {
                    card_info.put("total_user", true);
                }
            }
            card_info_list.add(card_info);

            // 自定义字段，表示支付后领券
            JSONObject custom_info = new JSONObject(1);
            custom_info.put("type", "AFTER_PAY_PACKAGE");

            // 拼装json对象
            info.put("basic_info", basic_info);
            info.put("card_info_list", card_info_list);
            info.put("custom_info", custom_info);
            paramJson.put("info", info);

            // 请求微信接口，得到返回结果[json]
            String result = HttpClientUtils.doPost(WxUrlConstant.WX_CARD_ACTIVITY_CREATE_URL.concat(accessToken), JSON.toJSONString(paramJson));
            JSONObject resultJson = JSON.parseObject(result);
            // {"errcode":0,"errmsg":"ok","activity_id":"4728935"}
            System.out.println(resultJson.toJSONString());

            return resultJson;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("================" + e.getMessage());
        }
        return null;
    }

    /**
      * 方法表述: 创建活动接口之前的验证
      * @Author zoujiulong
      * @Date 18:32 2019/6/11
      * @param       begin_time 活动开始时间，精确到秒
     * @param       end_time 活动结束时间，精确到秒
     * @param       gift_num 单个礼包社交立减金数量（3-15个）
     * @param       max_partic_times_act 每个用户活动期间最大领取次数,最大为50，默认为1
     * @param       max_partic_times_one_day 每个用户活动期间单日最大领取次数,最大为50，默认为1
     * @param       min_amt 最少支付金额，单位是元
      * @return java.lang.String
    */
    public String checkCardActivity(String begin_time, String end_time, int gift_num, int max_partic_times_act,
                                    int max_partic_times_one_day, String min_amt) {

        // 开始时间不能小于结束时间
        if (DateFormUtils.latterThan(end_time, begin_time, DateFormatterConstant.TIME_FORMAT_NORMAL)) {
            return "活动开始时间不能小于活动结束时间";
        }

        // 单个礼包社交立减金数量（3-15个）
        if (gift_num < 3 || gift_num > 15) {
            return "单个礼包社交立减金数量（3-15个）";
        }

        // 每个用户活动期间最大领取次数,最大为50，默认为1
        if (max_partic_times_act <= 0 || max_partic_times_act > 50) {
            return "每个用户活动期间最大领取次数,最大为50，默认为1";
        }

        // 每个用户活动期间单日最大领取次数,最大为50，默认为1
        if (max_partic_times_one_day <= 0 || max_partic_times_one_day > 50) {
            return "每个用户活动期间单日最大领取次数,最大为50，默认为1";
        }

        // 最少支付金额，单位是元
        if (BigDecimal.ONE.compareTo(new BigDecimal(min_amt)) > 0) {
            return "最少支付金额必须大于1元";
        }

        return null;
    }

}
