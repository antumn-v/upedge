package com.upedge.oms.modules.stock.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class CustomerStockRecord{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 用户ID
	 */
    private Long customerId;
	/**
	 * 产品ID
	 */
    private Long productId;
	/**
	 * 变体ID
	 */
    private Long variantId;
	/**
	 * 仓库ID
	 */
    private String warehouseCode;
	/**
	 * 订单id
	 */
    private Long relateId;
	/**
	 * 交易类型  增加=0，抵扣=1，退款=2
	 */
    private Integer type;
	/**
	 * 0=普通订单，1=批发订单,2=
	 */
    private Integer orderType;
	/**
	 * 数量变动
	 */
    private Integer quantity;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 
	 */
    private String variantImage;

    private String variantSku;

    private String variantName;

	private Integer revokeState;

	private Integer customerShowState;

	public CustomerStockRecord() {
	}

	public CustomerStockRecord(Long id, Long customerId, Long productId, Long variantId, String warehouseCode, Long relateId, Integer type, Integer orderType, Integer quantity, Date createTime, Date updateTime, String variantImage, String variantSku, String variantName, Integer revokeState, Integer customerShowState) {
		this.id = id;
		this.customerId = customerId;
		this.productId = productId;
		this.variantId = variantId;
		this.warehouseCode = warehouseCode;
		this.relateId = relateId;
		this.type = type;
		this.orderType = orderType;
		this.quantity = quantity;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.variantImage = variantImage;
		this.variantSku = variantSku;
		this.variantName = variantName;
		this.revokeState = revokeState;
		this.customerShowState = customerShowState;
	}
}
