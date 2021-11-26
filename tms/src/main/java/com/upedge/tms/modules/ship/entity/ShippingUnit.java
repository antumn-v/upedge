package com.upedge.tms.modules.ship.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class ShippingUnit{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 运输方式id
	 */
    private Long methodId;
	/**
	 * 
	 */
    private String methodName;
	/**
	 * 始发地id
	 */
    private String fromAreaId;
	/**
	 * 目的地id
	 */
    private String toAreaId;
	/**
	 * 区间开始重量
	 */
    private BigDecimal startWeight;
	/**
	 * 区间结束重量
	 */
    private BigDecimal endWeight;
	/**
	 * 固定费+挂号费
	 */
    private BigDecimal fixedFee;
	/**
	 * 首重
	 */
    private BigDecimal firstWeight;
	/**
	 * 首重运费
	 */
    private BigDecimal firstFreight;
	/**
	 * 续重单位重量
	 */
    private BigDecimal continueUnitWeight;
	/**
	 * 续重单价
	 */
    private BigDecimal continueUnitPrice;
	/**
	 * 预计到达时间
	 */
    private Integer deliveryMinDay;
	/**
	 * 
	 */
    private Integer deliveryMaxDay;
	/**
	 * 折扣
	 */
    private BigDecimal discount;
	/**
	 * 
	 */
    private String remarks;
	/**
	 * 创建时间
	 */
    private Date createTime;
	/**
	 * 0:禁用 1:启用
	 */
    private Integer state;

}
