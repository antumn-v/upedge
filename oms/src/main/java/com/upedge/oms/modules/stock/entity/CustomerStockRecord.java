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
}
