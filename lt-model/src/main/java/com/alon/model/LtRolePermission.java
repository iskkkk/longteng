package com.alon.model;

import lombok.Data;

/**
 * @ClassName LtRolePermission
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/23 18:40
 * @Version 1.0
 **/
@Data
public class LtRolePermission {
    private Long id;

    private Long roleId;

    private Long perId;
}
