package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class PackageInfo{

	/**
	 * 
	 */
    private Integer packageId;
	/**
	 * 
	 */
    private String packageCountry;
	/**
	 * 
	 */
    private Date shipTime;
	/**
	 * 
	 */
    private Integer transportId;
	/**
	 * 
	 */
    private String transportName;
	/**
	 * 
	 */
    private Integer packageWeight;
	/**
	 * 
	 */
    private BigDecimal finalShippingFee;
	/**
	 * 
	 */
    private String logisticsId;
	/**
	 * 
	 */
    private String trackNumber;
	/**
	 * 
	 */
    private BigDecimal orderAmount;
	/**
	 * 
	 */
    private BigDecimal purchaseAmount;
	/**
	 * 
	 */
    private String clientSku;
	/**
	 * 
	 */
    private Integer quantity;
	/**
	 * 
	 */
    private String operationUsername;
	/**
	 * 
	 */
    private Integer orderCount;
	/**
	 * 
	 */
    private Integer infoCount;
	/**
	 * 
	 */
    private Date shipDate;
	/**
	 * 
	 */
    private String updateToken;
	/**
	 * 
	 */
    private Long handleTime;
	/**
	 * 包裹生成时间
	 */
    private Date packageAddTime;

}
