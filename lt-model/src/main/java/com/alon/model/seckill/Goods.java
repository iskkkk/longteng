package com.alon.model.seckill;

import lombok.Data;

/**
 * @ClassName Goods
 * @Description 商品信息
 * @Author 一股清风
 * @Date 2019/5/17 14:53
 * @Version 1.0
 **/
@Data
public class Goods {
    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;
}
