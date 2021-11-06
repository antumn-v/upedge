package com.upedge.thirdparty.woocommerce.moudles.order.entity;

import lombok.Data;

@Data
public class WoocommerceOrderAddress {
    private String first_name;
    private String last_name;
    private String company;
    private String address_1;
    private String address_2;
    private String city;
    private String state;
    private String postcode;
    private String country;

}
