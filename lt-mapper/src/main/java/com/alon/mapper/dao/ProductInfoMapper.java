package com.alon.mapper.dao;

import com.alon.model.ProductInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInfoMapper {
    List<ProductInfo> getInfo(ProductInfo info);
}
