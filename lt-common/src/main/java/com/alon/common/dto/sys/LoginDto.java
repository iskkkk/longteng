package com.alon.common.dto.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName LoginDto
 * @Description 登陆注册参数
 * @Author 一股清风
 * @Date 2019/5/24 10:11
 * @Version 1.0
 **/
public class LoginDto implements Serializable {
    public String userName;
    public String password;
    public Boolean rememberMe;
    public Long id;
    public String salt;
    public String head;
    /**
     * 注册时间
     */
    public Date registerDate;
    /**
     * 上次登录时间
     */
    public Date lastLoginDate;
    /**
     * 登录次数
     */
    public Integer loginCount;

    public Integer six;

    public Date birthday;

    public Integer isMember;

    public Date createTime;

    public Date updateTime;

    public Integer status;

    public long updateVersion;
    public String unionId;
    public String phone;
}
