package com.upedge.oms.modules.stock.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdminStockOrderItemVo {

    /**
     *
     */
    private Long id;
    /**
     * 产品ID
     */
    private Long productId;
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

}
