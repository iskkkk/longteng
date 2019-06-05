package com.alon.common.third.wx;

import com.alibaba.fastjson.JSONObject;
import com.alon.common.config.LtConfigParams;
import com.alon.common.constant.WxUrlConstant;
import com.alon.common.result.ResultData;
import com.alon.common.utils.HttpClientUtils;
import com.alon.common.vo.wx.WxUserBaseInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName UserBaseInfoUtil
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/21 14:38
 * @Version 1.0
 **/
@Slf4j
@Component
public class UserBaseInfoUtil {

    @Autowired
    private LtConfigParams params;

    public ResultData<WxUserBaseInfoVo> getUserInfo(String code) {
        String appid = params.appId;
        String secret = params.appSecret;
        String url = WxUrlConstant.USER_GRANT_URL + "access_token?";
        Map<String,Object> map = new TreeMap<>();
        map.put("appid",appid);
        map.put("secret",secret);
        map.put("code",code);
        map.put("grant_type","authorization_code");
        map = HttpClientUtils.doGet(url,map);
        WxUserBaseInfoVo user = new WxUserBaseInfoVo();
        user.openid = (String) map.get("openid");
        user.accessToken = (String) map.get("access_token");
        user.refreshToken = (String) map.get("refresh_token");
        user.expiresIn = (Integer) map.get("expires_in");
        user.scope = (String) map.get("scope");
        user.errcode = (Integer) map.get("errcode");
        user.errmsg = (String) map.get("errmsg");
        log.info("map:" + map);
        JSONObject userInfo = null;
        if (null == user.errcode) {
            //拉取用户信息(需scope为 snsapi_userinfo)
            String returnUrl="https://api.weixin.qq.com/sns/userinfo?access_token="+user.accessToken
                    + "&openid="+user.openid
                    + "&lang=zh_CN";
            userInfo = HttpClientUtils.getJsonObject(returnUrl);
            log.info("用户信息：" + userInfo);
        }
        user = setUserInfo(user, userInfo);
        return ResultData.success(user);
    }

    /**
      * 方法表述: 设置用户信息
      * @Author 一股清风
      * @Date 18:13 2019/5/21
      * @param       user
     * @param       userInfo
      * @return com.alon.common.vo.wx.WxUserBaseInfoVo
    */
    protected static WxUserBaseInfoVo setUserInfo(WxUserBaseInfoVo user,JSONObject userInfo) {
        if (null != userInfo) {
            user.country = userInfo.getString("country");
            user.province = userInfo.getString("province");
            user.city = userInfo.getString("city");
            user.openid = userInfo.getString("openid");
            user.sex = userInfo.getInteger("sex");
            user.nickName = userInfo.getString("nickname");
            user.headImgUrl = userInfo.getString("headimgurl");
            user.language = userInfo.getString("language");
            user.privilege = userInfo.getString("privilege");
        }
        return user;
    }
}
