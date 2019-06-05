package com.alon.web.controller;

import com.alon.common.config.LtConfigParams;
import com.alon.common.dto.sys.LoginDto;
import com.alon.common.result.CodeMessage;
import com.alon.common.result.ResultData;
import com.alon.common.vo.LoginVo;
import com.alon.impl.redis.util.RedisUtil;
import com.alon.impl.seckill.UserService;
import com.alon.service.sys.LtUserService;
import com.alon.service.third.wx.WxInfoService;
import com.alon.common.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLEncoder;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/20 17:28
 * @Version 1.0
 **/
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private WxInfoService wxInfoService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LtUserService ltUserService;

    @Autowired
    private LtConfigParams params;

    @PostMapping("/do_login")
    public ResultData login(HttpServletResponse response, @Valid @RequestBody LoginVo loginVo) {
        log.info(loginVo.toString());
        String token = userService.login(response, loginVo);
        return ResultData.success(token);
    }

    @PostMapping("wx_login")
    public ResultData wxLogin() {
        //获取返回的回调地址
        String reutrnURl="http://aloning.imwork.net/login/callBack";
        //第一步：用户同意授权，获取code
        String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=".concat(params.appId)
                + "&redirect_uri=".concat( URLEncoder.encode(reutrnURl))
                + "&response_type=code"
                + "&scope=snsapi_userinfo"
                + "&state=STATE#wechat_redirect";
        log.info("====扫码链接======".concat(url));
        return ResultData.success(url);
    }

    @GetMapping("callBack")
    public ResultData getCode(HttpServletRequest req, HttpServletResponse resp) {
        String code = code = req.getParameter("code");
        //通过code换取网页授权access_token,微信返回的参数会自动返回
        log.info("code:" + code);
        ResultData info = wxInfoService.getUserInfo(code);
        if (info.getCode() != 0) {
            return ResultData.error(CodeMessage.WX_TOKEN);
        }
        return ResultData.success("授权成功");
    }

    @PostMapping("/auth_login")
    public ResultData checkLogin(@RequestBody LoginDto dto){
        if(null == dto.rememberMe){
            dto.rememberMe = false;
        }
        if (StringUtils.isBlank(dto.userName)) {
            return ResultData.error(CodeMessage.NONE_USER);
        }
        // 获取Subject对象
        Subject subject = ShiroUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(dto.userName, dto.password,dto.rememberMe);
        try{
            subject.login(token);
            return ResultData.success("登陆成功！");
        }catch (UnknownAccountException e) {
            return ResultData.error(CodeMessage.NONE_USER);
        } catch (IncorrectCredentialsException e) {
            return ResultData.error(CodeMessage.ERROR_PASS);
        } catch (LockedAccountException e) {
            return ResultData.error(CodeMessage.LOCKING);
        } catch (AuthenticationException e) {
            return ResultData.error(CodeMessage.AUTH_FAILED);
        }
    }

    @PostMapping("/doRegister")
    public ResultData doRegister(@RequestBody LoginDto dto) {
        boolean result = ltUserService.registerData(dto);
        if(result){
            return ResultData.success("注册成功");
        }
        return ResultData.error(CodeMessage.BIND_ERROR);
    }
}
