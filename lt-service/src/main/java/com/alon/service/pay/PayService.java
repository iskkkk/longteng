package com.alon.service.pay;

import com.alon.common.dto.pay.WxPayForm;
import com.alon.common.result.ResultData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface PayService {

    /**
      * 方法表述: 统一下单
      * @Author 一股清风
      * @Date 14:46 2019/5/13
      * @param       payForm
      * @return java.lang.String
    */
    ResultData wxPay(WxPayForm payForm);

    /**
      * 方法表述: 支付回调
      * @Author 一股清风
      * @Date 15:20 2019/5/13
      * @param       request
     * @param       response
      * @return java.util.Map<java.lang.String,java.lang.Object>
    */
    Map<String,Object> payNotify(HttpServletRequest request, HttpServletResponse response);
}
