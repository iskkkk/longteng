package com.alon.impl.product;

import com.alon.mapper.dao.ProductInfoMapper;
import com.alon.model.ProductInfo;
import com.alon.service.product.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoMapper infoMapper;

    @Override
    public List<ProductInfo> getInfo(ProductInfo info) {
        log.info("======进入service=========");
        return infoMapper.getInfo(info);
    }
}
