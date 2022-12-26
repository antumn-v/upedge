package com.upedge.oms.modules.pack.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class OrderPackageImportLog{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private String storeName;
	/**
	 * 
	 */
    private String platOrderName;
	/**
	 * 
	 */
    private String trackingCode;
	/**
	 * 
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
	 * 
	 */
    private Boolean state;
	/**
	 * 
	 */
    private Date importTime;
	/**
	 * 
	 */
    private String failReason;
	/**
	 * 0=其他  1=店小秘
	 */
    private Integer importSource;

	private Long orderId;

}
