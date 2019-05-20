package com.alon.model.seckill;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName OrderInfo
 * @Description 订单信息
 * @Author 一股清风
 * @Date 2019/5/17 14:54
 * @Version 1.0
 **/
@Data
public class OrderInfo {
    private Long id;
    private Long userId;
    private Long goodsId;
    private Long  deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private Integer orderChannel;
    private Integer status;
    private Date createDate;
    private Date payDate;
}
