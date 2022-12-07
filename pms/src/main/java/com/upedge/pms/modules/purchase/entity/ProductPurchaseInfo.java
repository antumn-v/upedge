package com.upedge.pms.modules.purchase.entity;

import lombok.Data;

/**
 * @author gx
 */
@Data
public class ProductPurchaseInfo{

	/**
	 * 
	 */
    private String purchaseSku;

	private String variantName;

	private String variantImage;
	/**
	 * 
	 */
    private String purchaseLink;
	/**
	 * 
	 */
    private String supplierName;
	/**
	 * 
	 */
    private String specId;

	private Integer inventory;

}
