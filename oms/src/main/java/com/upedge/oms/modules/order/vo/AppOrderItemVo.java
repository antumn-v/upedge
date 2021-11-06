package com.upedge.oms.modules.order.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AppOrderItemVo {

    private Long id;
    /**
     *
     */
    private Long orderId;

    private Long storeOrderId;

    private Long storeProductId;
    /**
     *
     */
    private Long adminProductId;

    private Long adminVariantId;
    /**
     *
     */
    private String storeVariantName;
    /**
     *
     */
    private String storeVariantSku;
    /**
     *
     */
    private String adminVariantImage;
    /**
     *
     */
    private String storeProductTitle;
    /**
     *
     */
    private BigDecimal price;

    private BigDecimal adminVariantWeight;

    private BigDecimal adminVariantVolume;
    /**
     *
     */
    private Integer quantity;
    /**
     * 抵扣数量
     */
    private Integer dischargeQuantity;

    private Integer itemType;

}
