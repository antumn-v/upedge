package com.upedge.common.model.tms;

import lombok.Data;

@Data
public class WarehouseVo {

    private Long id;
    /**
     *
     */
    private String warehouseCode;
    /**
     * 0=本地仓 1=海外仓
     */
    private Integer warehouseType;
    /**
     *
     */
    private String warehouseName;
    /**
     *
     */
    private String warehouseEnEame;
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
    /**
     *
     */
    private String email;
    /**
     *
     */
    private String phone;
    /**
     * 0=禁用 1=启用
     */
    private Integer state;
}
