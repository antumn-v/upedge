package com.upedge.common.model.old.oms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
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
    private Long userId;
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
