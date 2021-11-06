package com.upedge.oms.modules.wholesale.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class WholesaleExcelRecord{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private String storeName;
	/**
	 * 
	 */
    private String customerOrderNumber;
	/**
	 * 
	 */
    private Long wholesaleOrderId;
	/**
	 * 
	 */
    private Date createTime;

}
