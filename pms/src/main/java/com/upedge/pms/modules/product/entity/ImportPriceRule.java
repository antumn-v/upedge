package com.upedge.pms.modules.product.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gx
 */
@Data
public class ImportPriceRule{

	/**
	 * 
	 */
private Long id;
	/**
	 * 
	 */
private Long userId;
	/**
	 * 
	 */
private BigDecimal rangeStart;
	/**
	 * 
	 */
private BigDecimal rangeEnd;
	/**
	 * 
	 */
private Integer markupType;
	/**
	 * 
	 */
private BigDecimal markupVal;
	/**
	 * 
	 */
private Integer compareMarkupType;
	/**
	 * 
	 */
private BigDecimal compareMarkupVal;
	/**
	 * 
	 */
private Long customerId;
	/**
	 * 
	 */
private Date createTime;
	/**
	 * 
	 */
private Date updateTime;

}
