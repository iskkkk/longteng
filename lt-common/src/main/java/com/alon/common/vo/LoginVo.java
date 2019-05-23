package com.alon.common.vo;

import com.alon.common.validator.IsMobile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName LoginVo
 * @Description 登录参数
 * @Author 一股清风
 * @Date 2019/5/20 14:44
 * @Version 1.0
 **/
public class LoginVo implements Serializable {
    @NotNull
    @IsMobile  //因为框架没有校验手机格式注解，所以自己定义
    private String mobile;

    @NotNull
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
