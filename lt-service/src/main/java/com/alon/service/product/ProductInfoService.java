package com.alon.service.product;

import com.alon.model.ProductInfo;

import java.util.List;

public interface ProductInfoService {
    /**
      * 方法表述: 获取商品信息(ctrl+alt+v 快速填充)
      * @Author 一股清风
      * @Date 14:43 2019/5/7
      * @param       info
      * @return java.util.List<com.alon.model.ProductInfo>
    */
    List<ProductInfo> getInfo(ProductInfo info);

    /**
      * 方法表述: 将数据从mysql中上传到es中
      * @Author 一股清风
      * @Date 17:43 2019/5/7
      * @param       info
      * @return void
    */
    void saveEsInfo(ProductInfo info);
}
