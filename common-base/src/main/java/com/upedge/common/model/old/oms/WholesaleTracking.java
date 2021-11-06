package com.upedge.common.model.old.oms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class WholesaleTracking{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private String trackingCode;
	/**
	 * 0:真实追踪号 1:物流商单号
	 */
    private Integer trackingType;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 
	 */
    private String adminUserId;
	/**
	 * 
	 */
    private String shippingMethodName;
	/**
	 * 赛盒运输id
	 */
    private Integer transportId;
	/**
	 * 
	 */
    private String trackingUrl;

}
