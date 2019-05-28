package com.alon.impl.third.wx;

import com.alon.common.result.CodeMessage;
import com.alon.common.result.ResultData;
import com.alon.common.third.wx.UserBaseInfoUtil;
import com.alon.common.third.wx.WxTokenUtil;
import com.alon.common.vo.wx.WxUserBaseInfoVo;
import com.alon.impl.redis.util.RedisUtil;
import com.alon.model.WxUserInfo;
import com.alon.service.WxUserInfoService;
import com.alon.service.third.wx.WxInfoService;
import com.alon.service.third.wx.WxTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @ClassName WxInfoServiceImpl
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/21 16:34
 * @Version 1.0
 **/
@Service
@Slf4j
public class WxInfoServiceImpl implements WxInfoService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private WxUserInfoService userInfoService;

    @Autowired
    private WxTokenService tokenService;

    @Override
    public ResultData getUserInfo(String code) {
        WxUserInfo userInfo = null;
        ResultData data = UserBaseInfoUtil.getUserInfo(code);
        WxUserBaseInfoVo infoVo = (WxUserBaseInfoVo) data.getData();
        String token = (String) tokenService.getAccessToken().getData();
        infoVo = WxTokenUtil.getMoreInfo(token,infoVo.openid);
        if (StringUtils.isNotBlank(infoVo.openid)) {
            userInfo = new WxUserInfo();
            BeanUtils.copyProperties(infoVo,userInfo);
        } else {
            return ResultData.error(new CodeMessage(Integer.valueOf(infoVo.errcode),infoVo.errmsg));
        }
        if (CollectionUtils.isEmpty(userInfoService.getInfoForIpenId(userInfo))) {
            userInfoService.insert(userInfo);
        }
        return ResultData.success(infoVo);
    }

}
