package com.alon.common.third.wx;

import com.alon.common.constant.WxUrlConstant;
import com.alon.common.result.ResultData;
import com.alon.common.utils.HttpClientUtils;
import com.alon.common.vo.wx.TokenVo;

import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName WxTokenUtil
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/21 14:04
 * @Version 1.0
 **/
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
}
