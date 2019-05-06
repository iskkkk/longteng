package com.alon.service.product;

import com.alon.model.ProductInfo;

import java.util.List;

public interface ProductInfoService {
    /**
     * 获取商品信息
     * @auther: zoujiulong
     * @date: 2019/5/5   18:49
    * @param info
     *
     */
    List<ProductInfo> getInfo(ProductInfo info);
}
