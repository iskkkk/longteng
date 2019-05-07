package com.alon.service.product;

import com.alon.model.ProductInfo;

import java.util.List;

public interface ProductInfoService {
    /**
      * @Author 一股清风
      * @Description 获取商品信息
      * @Date 19:23 2019/5/6
     * @param       info
      * @return java.util.List<com.alon.model.ProductInfo>
    */
    List<ProductInfo> getInfo(ProductInfo info);
}
