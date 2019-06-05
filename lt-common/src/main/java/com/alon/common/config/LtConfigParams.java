package com.alon.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName LtConfigParams
 * @Description 获取配置文件中的参数
 * @Author zoujiulong
 * @Date 2019/6/5 15:54
 * @Version 1.0
 **/
@Component
public class LtConfigParams {
    @Value("${lt.wx.appId}")
    public String appId;

    @Value("${lt.wx.appSecret}")
    public String appSecret;

    @Value("${lt.wx.mchId}")
    public String mchId;

    @Value("${lt.wx.key}")
    public String key;

}
