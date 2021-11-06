package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xwCui
 */
@Data
public class AppStockOrderRefundItem{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 退款id
	 */
    private Long refundId;
	/**
	 * 变体数量
	 */
    private Integer quantity;
	/**
	 * 单个价格
	 */
    private BigDecimal unitPrice;
	/**
	 * 关联退款订单项id
	 */
    private Long relateId;
	/**
	 * 
	 */
    private Long productId;
	/**
	 * 
	 */
    private Long variantId;

}
