package com.upedge.pms.modules.purchase.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class PurchaseOrderImItem{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long orderImId;
	/**
	 * 
	 */
    private Long variantId;
	/**
	 * 
	 */
    private Integer quantity;

}
