package com.alon.impl.product;

import com.alon.mapper.dao.ProductInfoMapper;
import com.alon.model.ProductInfo;
import com.alon.service.product.ProductInfoService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoMapper infoMapper;
    @Autowired
    private JestClient jestClient;

    @Override
    public List<ProductInfo> getInfo(ProductInfo info) {
        log.info("======进入service=========");
        List<ProductInfo> infos = new ArrayList<ProductInfo>();
        try {
            infos = infoMapper.getInfo(info);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        return infos;
    }

    @Override
    public void saveEsInfo(ProductInfo info) {
        Index index = new Index.Builder(info).index("lt").type("productInfo").id(String.valueOf(info.getId()))
                .build();
        try {
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
