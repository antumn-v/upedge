package com.upedge.thirdparty.woocommerce.moudles.shop.entity;

import lombok.Data;

import java.util.List;

@Data
public class WoocWebhook {


    private int id;
    private String name;
    private String status;
    private String topic;
    private String resource;
    private String event;
    private String delivery_url;
    private String date_created;
    private String date_created_gmt;
    private String date_modified;
    private String date_modified_gmt;

}
