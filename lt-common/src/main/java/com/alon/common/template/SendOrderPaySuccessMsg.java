package com.alon.common.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alon.common.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @ClassName SendOrderPaySuccessMsg
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/29 18:14
 * @Version 1.0
 **/
@Slf4j
public class SendOrderPaySuccessMsg {
    public static void sendMessage(String access_token, String openId) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;

        Map<String, Object> templateValue = new HashMap<String, Object>(6);
        templateValue.put("orderNo","test123456");
        templateValue.put("products","123456");
        templateValue.put("payFee","Alon");
        templateValue.put("statusName","Alon");
        templateValue.put("createTime","2019-05-30");
        String send = MsgPushTemplate.processTemplate("SEND", templateValue);
        WeiXinTemplateMsgForm msgForm = JSON.parseObject(send, new TypeReference<WeiXinTemplateMsgForm>() {
        });
        IntStream.range(1,10).forEach(i -> {
            msgForm.setOpenId(openId);
            sendTemplateMessage(url,msgForm);
        });

    }
    /**
      * 方法表述: 发送模板消息
      * @Author zoujiulong
      * @Date 16:12 2019/6/4 
      * @param       url
     * @param       msgForm
      * @return void
    */
    private static void sendTemplateMessage(String url,WeiXinTemplateMsgForm msgForm) {
        String param = WeiXinTemplateMsgForm.getRequestParam(msgForm);
        log.info("sendWeixinTemplateMessage strJsonReqParam:" + msgForm);
        String data = HttpClientUtils.doPost(url, param);
        log.info("sendWeixinTemplateMessage syncApiPostJson after either：" + data);
        JSONObject jsonObject = JSONObject.parseObject(data);
        log.info("===========" + jsonObject);
        int result = 0;
        if (null != jsonObject) {
            if (0 != jsonObject.getInteger("errcode")) {
                result = jsonObject.getInteger("errcode");
                log.error("错误 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
            }
        }
        log.info("模板消息发送结果："+result);
    }
}
