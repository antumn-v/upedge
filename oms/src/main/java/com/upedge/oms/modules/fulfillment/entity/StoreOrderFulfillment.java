package com.upedge.oms.modules.fulfillment.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class StoreOrderFulfillment{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 
	 */
    private Long storeOrderId;
	/**
	 * 
	 */
    private String platOrderId;
	/**
	 * 
	 */
    private String platFulfillmentId;
	/**
	 * 
	 */
    private String platFulfillmentStatus;
	/**
	 * 
	 */
    private Long storeId;
	/**
	 * 
	 */
    private Long orgId;
	/**
	 * 0=上传，1=修改
	 */
    private Integer fulfillmentType;

    //0=失败，1=成功
    private Boolean fulfillmentResult;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private Date createTime;

}
