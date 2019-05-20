package com.alon.model.seckill;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName LtUser
 * @Description 用户信息
 * @Author 一股清风
 * @Date 2019/5/17 14:58
 * @Version 1.0
 **/
@Data
public class LtUser {
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;
}
