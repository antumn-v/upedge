package com.upedge.thirdparty.shoplazza.moudles.product.entity;

import lombok.Data;

@Data
public class ShoplazzaImage {

    private String id;
    private String product_id;
    private Integer position;
    private String src;
    private Integer width;
    private Integer height;
    private String alt;
    private String created_at;
    private String updated_at;

}
