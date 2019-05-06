package com.alon.mapper.dao;

import com.alon.model.ProductInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductInfoMapper {
    List<ProductInfo> getInfo(ProductInfo info);
}
