package com.upedge.sms.modules.overseaWarehouse.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class OverseaWarehouseSku{

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
    private String warehouseSkuId;
	/**
	 * 
	 */
    private String warehouseSkuCode;
	/**
	 * 
	 */
    private Date createTime;

}
