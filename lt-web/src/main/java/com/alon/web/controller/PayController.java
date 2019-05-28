package com.alon.web.controller;

import com.alon.common.dto.pay.WxPayForm;
import com.alon.common.result.ResultData;
import com.alon.service.pay.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName PayController
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/28 14:34
 * @Version 1.0
 **/
@RestController
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private PayService payService;

    @PostMapping("/wx_pay")
    public ResultData wxPay() {
        WxPayForm payForm = new WxPayForm();
        payForm.appId = "";
        payForm.mchId = "";
        payForm.key = "";
        String s = payService.wxScanPay(payForm);
        return  ResultData.success(s);
    }

    @GetMapping("/notify")
    public void payNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = payService.payNotify(request, response);
        log.info("===============" + map);
    }
}
