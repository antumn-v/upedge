package com.upedge.oms.modules.stock.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class StockOrderItem{

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
	 * 购物车ID
	 */
    private Long cartId;
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

    private String saiheSku;
	/**
	 * 已入库数量
	 */
	private Integer inboundQuantity;
	/**
	 * 采购单号
	 */
	private String purchaseNo;

}
