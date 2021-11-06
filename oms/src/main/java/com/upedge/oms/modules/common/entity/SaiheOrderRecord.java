package com.upedge.oms.modules.common.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class SaiheOrderRecord{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 客户自定义单号
	 */
    private String clientOrderCode;
	/**
	 * 
	 */
    private Integer orderType;
	/**
	 * 赛盒订单号
	 */
    private String saiheOrderCode;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private String managerCode;
	/**
	 * 
	 */
    private String orderSourceId;
	/**
	 * 
	 */
    private String orderSourceName;
	/**
	 * app运输方式ID
	 */
    private Long shipMethodId;
	/**
	 * 
	 */
    private Integer transportId;
	/**
	 * 
	 */
    private Date importTime;
	/**
	 * 0=失败，1=成功
	 */
    private Integer state;

	private String failReason;

}
