package com.alon.model;

import lombok.Data;

/**
 * @ClassName LtRole
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/23 18:22
 * @Version 1.0
 **/
@Data
public class LtRole {
    /**主键id*/
    private Long id;

    /**角色名称*/
    private String name;

    /**角色描述*/
    private String memo;
}
