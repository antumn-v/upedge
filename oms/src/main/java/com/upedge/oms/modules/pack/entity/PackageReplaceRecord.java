package com.upedge.oms.modules.pack.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class PackageReplaceRecord{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 换单包裹ID
	 */
    private Long packNo;
	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 原真实追踪号
	 */
    private String trackingCode;
	/**
	 * 原物流商单号
	 */
    private String logisticsOrderNo;
	/**
	 * 
	 */
    private Date createTime;

}
