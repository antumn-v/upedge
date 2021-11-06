package com.upedge.oms.modules.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AirwallexVo {

    /**
     * orderId 订单id
     */
    private Long orderId;

    /**
     *  prderDateTime 支付时间
     */
    private Date prderDateTime;

    /**
     * lineTotal 数量*价格
     */
    private BigDecimal lineTotal;

    /**
     * itemQuantity 数量
     */
    private Long itemQuantity;

    /**
     * admin_variant_sku
     */
    private String adminVariantSku;

    /**
     * admin_variant_id
     */
    private Long adminVariantId;

    /**
     *  usd_price
     */
    private BigDecimal usdPrice;

}
