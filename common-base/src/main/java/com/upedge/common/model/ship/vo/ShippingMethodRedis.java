package com.upedge.common.model.ship.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ShippingMethodRedis {

    /**
     *
     */
    private Long id;
    /**
     * 运输方式名称
     */
    private String name;

    private String methodCode;
    /**
     * 描述
     */
    private String desc;
    /**
     *
     */
    private Integer saiheTransportId;
    /**
     *
     */
    private String saiheTransportName;
    /**
     *
     */
    private String trackingUrl;

    private String trackingCompany;
    /**
     * 0:实重 1:体积重
     */
    private Integer weightType;
    /**
     * 追踪类型 0:真实追踪号 1:物流商单号
     */
    private Integer trackType;
    /**
     *
     */
    private String paypalCarrierEnum;
    /**
     * 0:禁用 1:启用
     */
    private Integer state;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    private String warehouseCode;
}
