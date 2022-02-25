package com.upedge.oms.modules.rules.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderShipRuleVo {

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private Long customerId;
    /**
     *
     */
    private String title;
    /**
     *
     */
    private Double orderAmountMin;
    /**
     *
     */
    private Double orderAmountMax;
    /**
     *
     */
    private Double freightMin;
    /**
     *
     */
    private Double freightMax;
    /**
     * 0=禁用 1= 启用 2=已删除
     */
    private Integer state;
    /**
     *
     */
    private Long shippingMethodId;

    private String shippingMethodName;

    private Long shipTemplateId;

    private Integer sequence;

    List<OrderShipRuleCountryVo> countries;
}
