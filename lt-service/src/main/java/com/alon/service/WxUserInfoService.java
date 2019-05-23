package com.alon.service;

import com.alon.model.WxUserInfo;

import java.util.List;

public interface WxUserInfoService {
    /**
      * 方法表述: 保存用户信息
      * @Author 一股清风
      * @Date 18:37 2019/5/21
      * @param       info
      * @return void
    */
    void insert(WxUserInfo info);

    List<WxUserInfo> getInfoForIpenId(WxUserInfo info);
}
