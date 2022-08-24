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

    private Long storeVariantId;

    private Long storeProductId;
    /**
     *
     */
    private Long adminProductId;

    private Long adminVariantId;

    private String barcode;
    /**
     *
     */
    private String storeVariantName;
    /**
     *
     */
    private String storeVariantSku;

    private String storeVariantImage;
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

    private String adminVariantSku;
    /**
     *
     */
    private Integer quantity;
    /**
     * 抵扣数量
     */
    private Integer dischargeQuantity;

    private Integer itemType;

    private Integer quoteState;

    private Integer pickedQuantity;

}
