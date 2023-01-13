package com.upedge.common.model.oms.stock;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 海桐
 */
@Data
public class StockOrderItemVo {

    /**
     *
     */
    private Long id;
    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 变体ID
     */
    private Long variantId;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 数量
     */
    private Integer quantity;

    private Integer refundQuantity;
    /**
     *
     */
    private String productTitle;
    /**
     *
     */
    private String variantName;
    /**
     *
     */
    private String variantSku;
    /**
     *
     */
    private String variantImage;

    private String supplierName;

    private String purchaseSku;

    private String barcode;

    private Integer stock;
}
