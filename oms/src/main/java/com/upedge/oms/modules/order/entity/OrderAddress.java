package com.upedge.oms.modules.order.entity;

import lombok.Data;

@Data
public class OrderAddress {

    private Long id;
    /**
     *
     */
    private String name;

    private String firstName;
    /**
     *
     */
    private String lastName;
    /**
     *
     */
    private String email;
    /**
     *
     */
    private String phone;
    /**
     *
     */
    private String country;
    /**
     *
     */
    private String province;
    /**
     *
     */
    private String city;
    /**
     *
     */
    private String address1;
    /**
     *
     */
    private String address2;
    /**
     *
     */
    private String zip;

    private String company;
    /**
     *
     */
    private String note;
    /**
     *
     */
    private String countryCode;
    /**
     *
     */
    private String provinceCode;
    /**
     *
     */
    private Long orderId;

    private Boolean ifEdited;

}
