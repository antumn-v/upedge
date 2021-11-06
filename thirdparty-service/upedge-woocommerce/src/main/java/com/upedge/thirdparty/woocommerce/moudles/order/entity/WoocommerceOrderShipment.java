package com.upedge.thirdparty.woocommerce.moudles.order.entity;

import lombok.Data;

import java.util.Date;

@Data
public class WoocommerceOrderShipment {

    private String tracking_id;
    private String tracking_provider;
    private String tracking_link;
    private String tracking_number;
    private Date date_shipped;


}
