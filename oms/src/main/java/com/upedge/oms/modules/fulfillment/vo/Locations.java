package com.upedge.oms.modules.fulfillment.vo;

import lombok.Data;

@Data
public class Locations {

    private Long id;
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String province;
    private String country;
    private String phone;
    private String country_code;
    private String country_name;
    private String province_code;
    private boolean legacy;
    private boolean active;

}
