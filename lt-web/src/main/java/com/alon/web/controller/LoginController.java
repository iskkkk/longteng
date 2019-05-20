package com.alon.web.controller;

import com.alon.common.result.ResultData;
import com.alon.common.vo.LoginVo;
import com.alon.impl.seckill.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

    @PostMapping("/do_login")
    public ResultData login(HttpServletResponse response, @Valid @RequestBody LoginVo loginVo) {
        log.info(loginVo.toString());
        String token = userService.login(response, loginVo);
        return ResultData.success(token);
    }
}
