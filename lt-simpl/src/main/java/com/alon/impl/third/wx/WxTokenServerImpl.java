package com.alon.impl.third.wx;

import com.alon.common.result.CodeMessage;
import com.alon.common.result.ResultData;
import com.alon.common.third.wx.WxTokenUtil;
import com.alon.common.vo.wx.TokenVo;
import com.alon.impl.redis.util.RedisConstants;
import com.alon.impl.redis.util.RedisUtil;
import com.alon.service.third.wx.WxTokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName WxTokenServerImpl
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/21 14:03
 * @Version 1.0
 **/
@Service
public class WxTokenServerImpl implements WxTokenService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResultData getAccessToken() {
        String token1 = (String) redisUtil.get("access_token", RedisConstants.datebase2);
        if (StringUtils.isBlank(token1)) {
            ResultData token = WxTokenUtil.getAccessToken();
            TokenVo tokenVo = (TokenVo) token.getData();
            if (null != tokenVo.token) {
                token1 = tokenVo.token;
                redisUtil.set("access_token",tokenVo.token, RedisConstants.datebase2,60 * 60 * 2L);
            } else if (!tokenVo.errcode.equals("0")) {
                //-1	系统繁忙，此时请开发者稍候再试
                //0	请求成功
                //40001	AppSecret错误或者AppSecret不属于这个公众号，请开发者确认AppSecret的正确性
                //40002	请确保grant_type字段值为client_credential
                //40164	调用接口的IP地址不在白名单中，请在接口IP白名单中进行设置。（小程序及小游戏调用不要求IP地址在白名单内。）
                String error = tokenVo.errmsg;
                return ResultData.error(new CodeMessage(Integer.valueOf(tokenVo.errcode),error));
            }
        }
        return ResultData.success(token1);
    }
}
