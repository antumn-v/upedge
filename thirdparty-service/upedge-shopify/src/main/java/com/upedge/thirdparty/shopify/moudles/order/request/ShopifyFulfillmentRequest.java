package com.upedge.thirdparty.shopify.moudles.order.request;

import com.upedge.thirdparty.shopify.moudles.order.entity.ShopifyLineItem;
import lombok.Data;

import java.util.List;

@Data
public class ShopifyFulfillmentRequest {

    ShopifyFulfillmentDto fulfillment;


    @Data
    public class ShopifyFulfillmentDto{
        private String location_id;
        private String tracking_number;
        private String tracking_url;
        private String tracking_company;
        private List<ShopifyLineItem> line_items;

        private List<String> tracking_numbers;

        private List<String> tracking_urls;
    }
}
