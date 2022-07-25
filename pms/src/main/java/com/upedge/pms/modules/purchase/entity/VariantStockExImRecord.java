package com.upedge.pms.modules.purchase.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class VariantStockExImRecord{

	//出库
	public static Integer EX_WAREHOUSE = 0;
	//入库
	public static Integer IM_WAREHOUSE = 1;

	/**
	 * 
	 */
    private Long id;
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
    private String warehouseCode;
	/**
	 * 
	 */
    private String trackingCode;
	/**
	 * 
	 */
    private Integer quantity;
	/**
	 * 
	 */
    private Long operatorId;
	/**
	 * 1=入库  0=出库
	 */
    private Integer operateType;
	/**
	 * 
	 */
    private Date createTime;

}
