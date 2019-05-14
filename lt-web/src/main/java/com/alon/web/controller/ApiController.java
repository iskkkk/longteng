package com.alon.web.controller;

import com.alon.model.ProductInfo;
import com.alon.service.product.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
@Slf4j
public class ApiController {

    @Autowired
    private ProductInfoService infoService;

    @PostMapping("/getInfo")
    public List<ProductInfo> getInfo() {
      log.info("======开始=========");
      return infoService.getInfo(new ProductInfo());
    }

    @PostMapping("/onSale")
    public String onSale() {
        List<ProductInfo> infos = infoService.getInfo(new ProductInfo());
        for (ProductInfo info : infos) {
            infoService.saveEsInfo(info);
        }
        return "OK";
    }

    @PostMapping("/getSearchInfo")
    public String getSearchInfo(ProductInfo info) {
        info.setSkuName("小米");
        infoService.getEsData(info);
        return "OK";
    }

}
