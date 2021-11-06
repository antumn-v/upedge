package com.upedge.oms.modules.stock.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class StockOrderRefundItem{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 退款id
	 */
    private Long stockRefundId;
	/**
	 * 变体数量
	 */
    private Integer quantity;
	/**
	 * 单个价格
	 */
    private BigDecimal price;
	/**
	 * 关联退款订单项id
	 */
    private Long stockOrderItemId;
	/**
	 * 
	 */
    private Long productId;
	/**
	 * 
	 */
    private Long variantId;
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

	/**
	 * 当前库存
	 */
	private Integer stock;
	/**
	 * 客户产品库存id
	 */
	private Long stockId;

}
