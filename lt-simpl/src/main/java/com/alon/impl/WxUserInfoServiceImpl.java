package com.alon.impl;

import com.alon.mapper.dao.WxUserInfoMapper;
import com.alon.model.WxUserInfo;
import com.alon.service.WxUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName WxUserInfoServiceImpl
 * @Description 保存微信用户信息
 * @Author 一股清风
 * @Date 2019/5/21 18:38
 * @Version 1.0
 **/
@Service
public class WxUserInfoServiceImpl implements WxUserInfoService {

    @Autowired
    private WxUserInfoMapper userInfoMapper;

    @Override
    public void insert(WxUserInfo info) {
        if (StringUtils.isNotBlank(info.getOpenid())) {
            userInfoMapper.insert(info);
        }
    }

    @Override
    public List<WxUserInfo> getInfoForIpenId(WxUserInfo info) {
        List<WxUserInfo> infos = userInfoMapper.getInfoForOpenId(info);
        return infos;
    }
}
