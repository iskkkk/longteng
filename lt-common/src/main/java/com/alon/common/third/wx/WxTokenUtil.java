package com.alon.common.third.wx;

import com.alibaba.fastjson.JSONArray;
import com.alon.common.constant.DateFormatterConstant;
import com.alon.common.constant.WxUrlConstant;
import com.alon.common.result.ResultData;
import com.alon.common.utils.DateFormUtils;
import com.alon.common.utils.HttpClientUtils;
import com.alon.common.vo.wx.TokenVo;
import com.alon.common.vo.wx.WxFansList;
import com.alon.common.vo.wx.WxUserBaseInfoVo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName WxTokenUtil
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/21 14:04
 * @Version 1.0
 **/
@Slf4j
public class WxTokenUtil {
    /**
      * 方法表述: 获取access_token
      * @Author 一股清风
      * @Date 14:05 2019/5/21
      * @return com.alon.common.result.ResultData
    */
    public static ResultData<TokenVo> getAccessToken(){
        String appid = "";
        String appsecret = "";
        TokenVo redisVo = new TokenVo();
        String url = WxUrlConstant.TOKEN_URL + "token?";
        Map<String,Object> map = new TreeMap<>();
        map.put("grant_type","client_credential");
        map.put("appid",appid);
        map.put("secret",appsecret);
        map = HttpClientUtils.doGet(url,map);
        redisVo.token = (String) map.get("access_token");
        redisVo.expiresIn = (Integer) map.get("expires_in");
        redisVo.errcode = (Integer) map.get("errcode");
        redisVo.errmsg = (String) map.get("errmsg");
        return ResultData.success(redisVo);
    }

    /**
      * 方法表述: 获取粉丝列表
      * @Author 一股清风
      * @Date 15:49 2019/5/27
      * @param       token
     * @param       nextOpenId
      * @return java.lang.String
    */
    public static WxFansList getFansListUrl(String token, String nextOpenId) {
        String url = WxUrlConstant.GET_FANS_LIST + "get?";
        Map<String,Object> map = new TreeMap<>();
        map.put("access_token",token);
        map.put("next_openid",nextOpenId);
        map = HttpClientUtils.doGet(url,map);
        WxFansList fansList = new WxFansList();
        fansList.total = (Integer) map.get("total");
        fansList.count = (Integer) map.get("count");
        fansList.data = (List) ((Map) map.get("data")).get("openid");
        fansList.nextOpenid = (String) map.get("next_openid");
        fansList.errcode = (Integer) map.get("errcode");
        fansList.errmsg = (String) map.get("errmsg");
        return fansList;
    }

    /**
      * 方法表述: openid获取用户信息
      * @Author 一股清风
      * @Date 16:12 2019/5/27
      * @param       accessToken
     * @param       openId
      * @return void
    */
    public static WxUserBaseInfoVo getMoreInfo(String accessToken, String openId) {
        String url = WxUrlConstant.UNIONID_URL;
        Map<String,Object> map = new TreeMap<>();
        map.put("access_token",accessToken);
        map.put("openid",openId);
        map.put("lang","zh_CN");
        map = HttpClientUtils.doGet(url,map);
        return setInfo(map);
    }

    /**
      * 方法表述: 注入信息
      * @Author 一股清风
      * @Date 17:12 2019/5/27
      * @param       map
      * @return com.alon.common.vo.wx.WxUserBaseInfoVo
    */
    protected static WxUserBaseInfoVo setInfo(Map<String,Object> map) {
        WxUserBaseInfoVo infoVo = new WxUserBaseInfoVo();
        log.info("基本信息（包括UnionID机制）: " + map);
        infoVo.country = (String) map.get("country");
        infoVo.province = (String) map.get("province");
        infoVo.city = (String) map.get("city");
        infoVo.openid = (String) map.get("openid");
        infoVo.sex = (Integer) map.get("sex");
        infoVo.nickName = (String) map.get("nickname");
        infoVo.headImgUrl = (String) map.get("headimgurl");
        infoVo.language = (String) map.get("language");
        infoVo.privilege = (String) map.get("privilege");
        infoVo.qrScene = (Integer) map.get("qr_scene");
        infoVo.subscribe = (Integer) map.get("subscribe");
        infoVo.tagIdList = String.valueOf((JSONArray) map.get("tagid_list"));
        infoVo.groupId = (Integer) map.get("groupid");
        infoVo.remark = (String) map.get("remark");
        Integer time = (Integer) map.get("subscribe_time");
        if (null != time) {
            infoVo.subscribeTime = DateFormUtils.stampToDate(Long.valueOf(time), DateFormatterConstant.YEAR_MONTH_DAY_HH_MM_SS);
        }
        infoVo.subscribeScene = (String) map.get("subscribe_scene");
        infoVo.qrSceneStr = (String) map.get("qr_scene_str");
        infoVo.unionId = (String) map.get("unionid");
        infoVo.errcode = (Integer) map.get("errcode");
        infoVo.errmsg = (String) map.get("errmsg");
        return infoVo;
    }
}
