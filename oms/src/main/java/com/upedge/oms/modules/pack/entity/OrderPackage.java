package com.upedge.oms.modules.pack.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class OrderPackage{

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
    private Long storeId;
	/**
	 * 
	 */
    private String orgPath;
	/**
	 * 
	 */
    private Long packageNo;
	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 
	 */
    private Long waveNo;
	/**
	 * 真实追踪号
	 */
    private String trackingCode;
	/**
	 * 物流商单号
	 */
    private String logisticsOrderNo;
	/**
	 * 物流公司
	 */
    private String trackingCompany;
	/**
	 * 运输方式
	 */
    private String trackingMethodName;
	/**
	 * 运输方式代码
	 */
    private String trackingMethodCode;
	/**
	 * 平台ID
	 */
    private String platId;
	/**
	 * 0=待发货，1=已发货
	 */
    private Integer packageState;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date sendTime;
	/**
	 * 
	 */
    private Date receiveTime;

	private String remark;

}
