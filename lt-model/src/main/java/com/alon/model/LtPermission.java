package com.alon.model;

import lombok.Data;

/**
 * @ClassName LtPermission
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/23 18:23
 * @Version 1.0
 **/
@Data
public class LtPermission {
    /**主键id*/
    private Long id;

    /**url地址*/
    private String url;

    /**url描述*/
    private String urlRemark;
}
