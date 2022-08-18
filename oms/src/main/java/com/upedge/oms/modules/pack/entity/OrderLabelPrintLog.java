package com.upedge.oms.modules.pack.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class OrderLabelPrintLog{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 
	 */
    private Long packNo;
	/**
	 * 
	 */
    private String labelUrl;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Long operatorId;

}
