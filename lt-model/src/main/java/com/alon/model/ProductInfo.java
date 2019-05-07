package com.alon.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 商品id
     */
    private Long spuId;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * sku名称
     */
    private String skuName;

    /**
     * 商品规格描述
     */
    private String skuDesc;

    /**
     * 品牌
     */
    private Long tmId;

    /**
     * 三级分类id
     */
    private Long catalog3Id;

    /**
     * 默认显示图片
     */
    private String skuDefaultImg;

    /**
     * 重量
     */
    private BigDecimal weight;

}
