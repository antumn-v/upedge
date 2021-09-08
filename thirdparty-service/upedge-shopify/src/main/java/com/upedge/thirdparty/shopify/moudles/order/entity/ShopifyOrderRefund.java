package com.upedge.thirdparty.shopify.moudles.order.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class ShopifyOrderRefund {


    private String id;
    private String order_id;
    private Date created_at;
    private String note;
    private Long user_id;
    private String processed_at;
    private Boolean restock;
    private List<ShopifyOrderRefundItem> refund_line_items;
    private List<ShopifyTransaction> transactions;
}
