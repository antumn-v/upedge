package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xwCui
 */
@Data
public class AppOrderVariant{

	/**
	 * 
	 */
    private Long aovId;
	/**
	 * 
	 */
    private Long variantId;
	/**
	 * 拆分后的订单ID
	 */
    private Long newOrderId;
	/**
	 * admin变体ID
	 */
    private Long adminVariantId;
	/**
	 * 
	 */
    private Long adminProductId;
	/**
	 * 
	 */
    private Long originalItemId;
	/**
	 * 
	 */
    private Long originalVariantId;
	/**
	 * 
	 */
    private Long originalProductId;
	/**
	 * 
	 */
    private String title;
	/**
	 * 
	 */
    private Integer quantity;
	/**
	 * 
	 */
    private Integer applyQuantity;
	/**
	 * 
	 */
    private Integer dischargeQuantity;
	/**
	 * 
	 */
    private BigDecimal price;
	/**
	 * 
	 */
    private String sku;
	/**
	 * 
	 */
    private String variantTitle;
	/**
	 * 
	 */
    private String vendor;
	/**
	 * 
	 */
    private Long productId;
	/**
	 * 
	 */
    private String requiresShipping;
	/**
	 * 
	 */
    private String taxable;
	/**
	 * 0=正常变体，1=捆绑变体，2=拆分订单变体 3=补发变体订单 4=定制包装
	 */
    private Integer itemType;
	/**
	 * 
	 */
    private BigDecimal fulfillableQuantity;
	/**
	 * 
	 */
    private String grams;
	/**
	 * 
	 */
    private String fulfillmentStatus;
	/**
	 * shopify订单ID
	 */
    private Long orderId;
	/**
	 * 
	 */
    private String variantImg;
	/**
	 * 
	 */
    private BigDecimal cost;
	/**
	 * 
	 */
    private Long orderShippingId;
	/**
	 * 供应商ID
	 */
    private String supplierId;
	/**
	 * 运输方式
	 */
    private String shipMethod;
	/**
	 * 运输时间
	 */
    private String shipTime;
	/**
	 * 运费
	 */
    private String shipMoney;
	/**
	 * 速卖通订单留言
	 */
    private String orderNote;
	/**
	 * 
	 */
    private Long fulfillmentId;
	/**
	 * 
	 */
    private Integer scale;
	/**
	 * 
	 */
    private String fulfillmentService;
	/**
	 * 
	 */
    private BigDecimal usdPrice;

}
