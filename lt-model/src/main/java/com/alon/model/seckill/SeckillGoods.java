package com.alon.model.seckill;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName SeckillGoods
 * @Description 秒杀商品
 * @Author 一股清风
 * @Date 2019/5/17 14:58
 * @Version 1.0
 **/
@Data
public class SeckillGoods {
    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
    private int version;
}
