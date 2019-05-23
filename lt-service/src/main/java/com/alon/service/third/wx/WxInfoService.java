package com.alon.service.third.wx;

import com.alon.common.result.ResultData;

public interface WxInfoService {
    /**
      * 方法表述: 用户授权获取用户信息
      * @Author 一股清风
      * @Date 16:33 2019/5/21
     * @param       code
      * @return com.alon.common.result.ResultData
    */
    ResultData getUserInfo(String code);
}
