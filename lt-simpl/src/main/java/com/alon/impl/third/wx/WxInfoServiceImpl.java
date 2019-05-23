package com.alon.impl.third.wx;

import com.alibaba.fastjson.JSONArray;
import com.alon.common.constant.DateFormatterConstant;
import com.alon.common.constant.WxUrlConstant;
import com.alon.common.result.CodeMessage;
import com.alon.common.result.ResultData;
import com.alon.common.third.wx.UserBaseInfoUtil;
import com.alon.common.utils.DateFormUtils;
import com.alon.common.utils.HttpClientUtils;
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

import java.util.Map;
import java.util.TreeMap;

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
        infoVo = getMoreInfo(infoVo);
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

    /**
      * 方法表述: 获取更多用户信息
      * @Author 一股清风
      * @Date 15:02 2019/5/22
      * @param       user
      * @return void
    */
    public WxUserBaseInfoVo getMoreInfo(WxUserBaseInfoVo user) {
        String accessToken = (String) tokenService.getAccessToken().getData();
        String url = WxUrlConstant.UNIONID_URL;
        Map<String,Object> map = new TreeMap<>();
        map.put("access_token",accessToken);
        map.put("openid",user.openid);
        map.put("lang","zh_CN");
        map = HttpClientUtils.doGet(url,map);
        log.info("基本信息（包括UnionID机制）: " + map);
        user.qrScene = (Integer) map.get("qr_scene");
        user.subscribe = (Integer) map.get("subscribe");
        user.tagIdList = String.valueOf((JSONArray) map.get("tagid_list"));
        user.groupId = (Integer) map.get("groupid");
        user.remark = (String) map.get("remark");
        Integer time = (Integer) map.get("subscribe_time");
        if (null != time) {
            user.subscribeTime = DateFormUtils.stampToDate(Long.valueOf(time), DateFormatterConstant.YEAR_MONTH_DAY_HH_MM_SS);
        }
        user.subscribeScene = (String) map.get("subscribe_scene");
        user.qrSceneStr = (String) map.get("qr_scene_str");
        user.unionId = (String) map.get("unionid");
        user.errcode = (Integer) map.get("errcode");
        user.errmsg = (String) map.get("errmsg");
        return user;
    }
}
