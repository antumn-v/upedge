package com.upedge.thirdparty.shopify.moudles.order.entity;

import lombok.Data;

import java.util.List;

@Data
public class ShopifyFulfillment {


    /**
     * location_id : 905684977
     * tracking_number : 123456789010
     * tracking_company : fed ex
     * tracking_url : https://www.new-fedex-tracking.com/?number=123456789010
     * notify_customer : true
     * line_items : [{"id":466157049},{"id":518995019},{"id":703073504}]
     */

    private String id;
    private String order_id;
    private Long location_id;
    private String tracking_number;
    private String tracking_company;
    private String tracking_url;
    private boolean notify_customer = true;
    private List<ShopifyLineItem> line_items;
    private String status;
    private List<String> tracking_urls;
    private List<String> tracking_numbers;


}


