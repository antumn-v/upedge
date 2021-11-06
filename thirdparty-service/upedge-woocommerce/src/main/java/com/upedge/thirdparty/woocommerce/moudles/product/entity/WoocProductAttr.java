package com.upedge.thirdparty.woocommerce.moudles.product.entity;

import lombok.Data;

import java.util.List;

@Data
public class WoocProductAttr{
    private String id;
    private String name;
    private Integer position;
    private Boolean visible;
    private Boolean variation;
    private List<String> options;

}