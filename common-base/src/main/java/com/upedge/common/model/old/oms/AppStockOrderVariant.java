package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xwCui
 */
@Data
public class AppStockOrderVariant{

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

}
