package com.upedge.common.model.old.oms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class PackageTracking{

	/**
	 * 
	 */
    private String trackNumber;
	/**
	 * 
	 */
    private Integer transportId;
	/**
	 * 
	 */
    private String trackingMoreCode;
	/**
	 * 
	 */
    private Integer state;
	/**
	 * 
	 */
    private Date onlineTime;
	/**
	 * 
	 */
    private Date signedTime;
	/**
	 * 
	 */
    private Date shipTime;
	/**
	 * pending  查询中    notfound 查询不到   transit  运输途中   pickup  到达待取   delivered  成功签收  undelivered    投递失败   exception  可能异常   expired   运输过久

	 */
    private String trackStatus;
	/**
	 * 
	 */
    private String trackInfo;
	/**
	 * 
	 */
    private Long appUserId;
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
    private Date updateTime;
	/**
	 * 
	 */
    private String clientOrderCode;
	/**
	 * 
	 */
    private String destination;

}
