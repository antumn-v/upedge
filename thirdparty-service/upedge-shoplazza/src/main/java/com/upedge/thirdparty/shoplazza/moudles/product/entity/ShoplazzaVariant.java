package com.upedge.thirdparty.shoplazza.moudles.product.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShoplazzaVariant {

    private String id;
    private String product_id;
    private Integer position;
    private String title;
    private ShoplazzaImage image;
    private BigDecimal price;
    private BigDecimal compare_at_price;
    private double weight;
    private String weight_unit;
    private Integer inventory_quantity;
    private String sku;
    private String barcode;
    private String note;
    private String option1;
    private String option2;
    private String option3;
    private String created_at;
    private String updated_at;



}
