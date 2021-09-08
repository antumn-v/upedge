package com.upedge.thirdparty.shopify.moudles.shop.entity;

import lombok.Data;

@Data
public class ShopifyLocation {


    private String id;
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String zip;
    private String province;
    private String country;
    private String phone;
    private String created_at;
    private String updated_at;
    private String country_code;
    private String country_name;
    private String province_code;
    private boolean legacy;
    private boolean active;
    private String admin_graphql_api_id;


}
