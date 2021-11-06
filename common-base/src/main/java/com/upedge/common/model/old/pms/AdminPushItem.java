package com.upedge.common.model.old.pms;

import lombok.Data;

/**
 * @author xwCui
 */
@Data
public class AdminPushItem{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long storeProductId;
	/**
	 * 
	 */
    private Long storeVariantId;
	/**
	 * 
	 */
    private Long sibVariantId;
	/**
	 * 
	 */
    private Long relateId;
	/**
	 * 
	 */
    private Integer scale;

}
