package com.upedge.thirdparty.saihe.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaiheOrderItem {

    /**
     * 卖家SKU
     */
    private String sellerSku;
    /**
     * 订单产品数量
     */
    private Integer productNum;
    /**
     * 订单产品销售单价
     */
    private BigDecimal salePrice;
    /**
     * OrderItemId
     */
    private String orderItemId;

    //==============================================

    private Long adminProductId;

    private String orderName;

    private String storeId;

}
