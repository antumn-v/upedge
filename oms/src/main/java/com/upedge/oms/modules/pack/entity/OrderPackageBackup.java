package com.upedge.oms.modules.pack.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class OrderPackageBackup{

	/**
	 * 包裹号
	 */
    private Long id;
	/**
	 * 
	 */
    private Long packNo;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private Long storeId;
	/**
	 * 
	 */
    private String orgPath;
	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 
	 */
    private Long pickId;
	/**
	 * 真实追踪号
	 */
    private String trackingCode;
	/**
	 * 物流商单号
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
	 * 运输方式代码
	 */
    private String trackingMethodCode;
	/**
	 * 平台ID
	 */
    private String platId;
	/**
	 * 0=未出库，1=已出库，-1=取消发货
	 */
    private Integer packageState;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date sendTime;
	/**
	 * 
	 */
    private Date receiveTime;
	/**
	 * 包裹备注
	 */
    private String remark;
	/**
	 * 
	 */
    private Integer waveNo;
	/**
	 * 是否已上传店铺
	 */
    private Boolean isUploadStore;
	/**
	 * 
	 */
    private Date backupTime;

}
