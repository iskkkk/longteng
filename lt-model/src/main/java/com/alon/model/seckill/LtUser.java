package com.alon.model.seckill;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName LtUser
 * @Description 用户信息
 * @Author 一股清风
 * @Date 2019/5/17 14:58
 * @Version 1.0
 **/
@Data
public class LtUser implements Serializable {
    private Long id;
    private String userName;
    private String password;
    private String salt;
    private String head;
    /**
     * 注册时间
     */
    private Date registerDate;
    /**
     * 上次登录时间
     */
    private Date lastLoginDate;
    /**
     * 登录次数
     */
    private Integer loginCount;

    private Integer six;

    private Date birthday;

    private Integer isMember;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private long updateVersion;
    private String phone;
    private String unionId;
}
