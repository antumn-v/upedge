package com.upedge.pms.modules.product.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class StoreCustomVariantRecord{

	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private String sku;
	/**
	 * 
	 */
    private String sellingLink;
	/**
	 * 
	 */
    private Long storeVariantId;
	/**
	 * 
	 */
    private Long creatorId;
	/**
	 * 
	 */
    private Date createTime;

}
