package com.alon.impl.pay;

import com.alon.common.dto.pay.WxPayForm;
import com.alon.common.utils.*;
import com.alon.impl.webSocket.WebSocketHandler;
import com.alon.service.pay.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName PayServiceImpl
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/13 15:22
 * @Version 1.0
 **/
@Slf4j
@Service
public class PayServiceImpl implements PayService {
    protected final static String UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    @Override
    public String wxScanPay(WxPayForm payForm) {
        String nonceStr = NumberUtil.getRandomString(6);

        BigDecimal orderFee = BigDecimal.valueOf(1); // 价格 单位是元
        String body = "Alon扫码支付";   // 商品名称
        String outTradeNo = OrderNoUtils.getOrderNo(); // 订单流水号和系统的订单号不是一个
        // 获取发起电脑 ip
        String spbillCreateIp = "192.168.1.8";
        // 回调接口
        String notifyUrl = "http://dongdong.vicp.net/api/pay/payNotify";
        String tradeType = "NATIVE";

        Map<String, String> payParams = new HashMap<String, String>();
        payParams.put("appid", payForm.appId);
        payParams.put("mch_id", payForm.mchId);
        payParams.put("nonce_str", nonceStr);
        payParams.put("body", body);
        payParams.put("out_trade_no", outTradeNo);
        payParams.put("total_fee", String.valueOf(orderFee));
        payParams.put("spbill_create_ip", spbillCreateIp);
        payParams.put("notify_url", notifyUrl);
        payParams.put("trade_type", tradeType);
        String sign = SignUtils.sign(payForm.key,payParams);
        payParams.put("sign",sign);
        String xml = XmlUtils.toXml(payParams, true);
        log.info("請求參數：" + xml);
        String resXml = HttpClientUtils.post(UNIFIEDORDER, xml);

        Map map = XmlUtils.doXMLParse(resXml);
        log.info("响应参数：" + resXml);
        String urlCode = (String) map.get("code_url");
        return urlCode;
    }

    /**
      * 方法表述: 生成支付二维码
      * @Author 一股清风
      * @Date 15:54 2019/5/13
      * @param       chl
      * @return java.lang.String
    */
    public static String QRfromGoogle(String chl){
        int widhtHeight = 300;
        String EC_level = "L";
        int margin = 0;
        chl = QrUtils.UrlEncode(chl);
        String QRfromGoogle = "http://chart.apis.google.com/chart?chs=" + widhtHeight + "x" + widhtHeight
                + "&cht=qr&chld=" + EC_level + "|" + margin + "&chl=" + chl;

        return QRfromGoogle;
    }

    @Override
    public Map<String, Object> payNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> result = new HashMap<>();
        //读取参数
        InputStream inputStream = null;
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        try {
            inputStream = request.getInputStream();
            String s ;
            in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((s = in.readLine()) != null){
                sb.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.info("io异常：" + e);
        }finally {
            try {
                in.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.info("关闭流失败：" + e);
            }
        }
        //解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        m = XmlUtils.doXMLParse(sb.toString());
        //过滤空 设置 TreeMap
        Map<Object,Object> packageParams = new TreeMap<Object,Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);
            String v = "";
            if(null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        // 账号信息
        WxPayForm payForm = new WxPayForm();
        String key = payForm.key; // key
        log.info("解析结果：" + packageParams);
        //判断签名是否正确
        if(SignUtils.isTenpaySign("UTF-8", packageParams,key)) {
            //------------------------------
            //处理业务开始
            //------------------------------
            String resXml = "";
            if("SUCCESS".equals((String)packageParams.get("result_code"))){
                // 这里是支付成功
                //////////执行自己的业务逻辑////////////////
                String mchId = (String)packageParams.get("mch_id");
                String openId = (String)packageParams.get("openid");
                String isSubscribe = (String)packageParams.get("is_subscribe");
                String outTradeNo = (String)packageParams.get("out_trade_no");
                String totalFee = (String)packageParams.get("total_fee");
                log.info("mch_id:"+mchId);
                log.info("openid:"+openId);
                log.info("is_subscribe:"+isSubscribe);
                log.info("out_trade_no:"+outTradeNo);
                log.info("total_fee:"+totalFee);
                //////////执行自己的业务逻辑////////////////
                log.info("支付成功");
                //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                result.put("outTradeNo",outTradeNo);
                //当前页面订单对应页面的websocket连接
                WebSocketHandler.sendMessage(outTradeNo,"支付成功");
            } else {
                log.info("支付失败,错误信息：" + packageParams.get("err_code"));
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                result.put("outTradeNo",null);
            }
            //------------------------------
            //处理业务完毕
            //------------------------------
            BufferedOutputStream out = null;
            try {
                out = new BufferedOutputStream(
                        response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    out.write(resXml.getBytes());
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.info("流关闭失败：" + e);
                }
            }
        } else{
            log.info("通知签名验证失败");
        }
        return result;
    }
}
