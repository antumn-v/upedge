package com.upedge.common.model.old.ums;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class UserPaypalTracker{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private Long appUserId;
	/**
	 * 
	 */
    private Long storeId;
	/**
	 * 
	 */
    private Long storeOrderId;
	/**
	 * 
	 */
    private Long appOrderId;
	/**
	 * 
	 */
    private String transactionId;
	/**
	 * 0=未上传，1=已上传
	 */
    private Integer trackingState;
	/**
	 * 
	 */
    private String trackingNumber;
	/**
	 * 
	 */
    private String shipMethodName;
	/**
	 * 
	 */
    private Date uploadTime;

}
