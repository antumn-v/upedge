package com.upedge.pms.modules.purchase.entity;

import java.io.Serializable;
import java.util.Date;
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

}
