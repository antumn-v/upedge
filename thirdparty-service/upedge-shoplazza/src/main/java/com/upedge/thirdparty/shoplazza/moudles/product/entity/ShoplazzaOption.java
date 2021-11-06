package com.upedge.thirdparty.shoplazza.moudles.product.entity;

import lombok.Data;

import java.util.List;

@Data
public class ShoplazzaOption {

    private String id;
    private String product_id;
    private String name;
    private Integer position;
    private List<String> values;
}
