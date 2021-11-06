package com.upedge.oms.modules.rules.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class OrderShipRule{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private String title;
	/**
	 * 
	 */
    private Double orderAmountMin;
	/**
	 * 
	 */
    private Double orderAmountMax;
	/**
	 * 
	 */
    private Double freightMin;
	/**
	 * 
	 */
    private Double freightMax;
	/**
	 * 0=禁用 1= 启用 2=已删除
	 */
    private Integer state;
	/**
	 * 
	 */
    private Long shippingMethodId;

    private String shippingMethodName;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 
	 */
    private Integer sequence;

}
