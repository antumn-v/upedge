package com.upedge.oms.modules.wholesale.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class WholesaleTracking{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 回传单号
	 */
    private String trackingCode;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 订单ID
	 */
    private Long orderId;
	/**
	 * 
	 */
    private String shippingMethodName;
	/**
	 * 追踪类型 0:真实追踪号 1:物流商单号
	 */
    private Integer trackType;
	/**
	 * 赛盒运输id
	 */
    private Integer transportId;
	/**
	 * 发货时间
	 */
    private Date createTime;

}
