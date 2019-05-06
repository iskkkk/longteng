package com.alon.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfo {

    private Long id;

    private String name;

    private Long spiId;

    private BigDecimal weight;

    private String productDesc;

}
