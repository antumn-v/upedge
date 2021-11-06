package com.upedge.oms.modules.order.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class OrderTracking{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 回传单号
	 */
    private String trackingCode;
	/**
	 * 真实追踪号
	 */
    private String trackNumbers;
	/**
	 * 物流商单号
	 */
    private String logisticsOrderNo;
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
	 * 物流回传状态
	 */
    private Integer state;
	/**
	 * 追踪类型 0:真实追踪号 1:物流商单号
	 */
    private Integer trackType;
	/**
	 * 赛盒运输id
	 */
    private Integer transportId;

    private Date createTime;

	/**
	 * 订单类型  2  普通  3 批发
	 */
	private Integer orderTrackingType;


}
