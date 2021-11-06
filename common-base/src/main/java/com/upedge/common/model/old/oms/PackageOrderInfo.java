package com.upedge.common.model.old.oms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class PackageOrderInfo{

	/**
	 * 
	 */
    private Integer packageId;
	/**
	 * 
	 */
    private String orderCode;
	/**
	 * 
	 */
    private String clientOrderCode;
	/**
	 * 
	 */
    private Date payTime;
	/**
	 * 
	 */
    private String firstName;
	/**
	 * 
	 */
    private String lastName;
	/**
	 * 
	 */
    private Integer orderSourceId;
	/**
	 * 
	 */
    private String orderSourceName;
	/**
	 * 
	 */
    private Integer orderSourceType;
	/**
	 * 
	 */
    private Date shipDate;
	/**
	 * 支付到发货的处理时长
	 */
    private Long handleTime;
	/**
	 * 
	 */
    private Long appUserId;
	/**
	 * 从包裹生成到发货的处理时长
	 */
    private Long packageShipDuration;
	/**
	 * 从支付到包裹生成的处理时长
	 */
    private Long payPackageDuration;

}
