package com.upedge.thirdparty.woocommerce.moudles.order.entity;

import lombok.Data;

@Data
public class WoocommerceOrderNote {


    private String id;
    private String author;
    private String date_created;
    private String date_created_gmt;
    private String note;
    private boolean customer_note;

}
