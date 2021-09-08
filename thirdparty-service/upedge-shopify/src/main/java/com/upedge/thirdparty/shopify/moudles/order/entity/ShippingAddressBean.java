package com.upedge.thirdparty.shopify.moudles.order.entity;

import lombok.Data;

@Data
public class ShippingAddressBean {

    private String first_name;
    private String address1;
    private String phone;
    private String city;
    private String zip;
    private String province;
    private String country;
    private String last_name;
    private String address2;
    private String company;
    private double latitude;
    private double Longitude;
    private String name;
    private String country_code;
    private String province_code;
}
