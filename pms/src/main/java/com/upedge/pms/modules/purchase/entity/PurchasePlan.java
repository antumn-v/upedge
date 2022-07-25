package com.upedge.pms.modules.purchase.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gx
 */
@Data
public class PurchasePlan{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private String purchaseSku;
	/**
	 * 
	 */
    private String supplierName;
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
    private String variantSku;
	/**
	 * 
	 */
    private String variantName;
	/**
	 * 
	 */
    private String variantImage;
	/**
	 * 
	 */
    private String purchaseLink;
	/**
	 * 
	 */
    private Integer quantity;

	private BigDecimal price;
	/**
	 * 
	 */
    private String specId;
	/**
	 * 
	 */
    private Long operatorId;
	/**
	 * 0=计划中 1=已生成 -1=已取消
	 */
    private Integer state;
	/**
	 * 
	 */
    private Long purchaseOrderId;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
