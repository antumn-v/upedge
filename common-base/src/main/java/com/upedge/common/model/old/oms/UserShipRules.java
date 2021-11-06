package com.upedge.common.model.old.oms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class UserShipRules{

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
    private String title;
	/**
	 * 
	 */
    private String countries;
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
	 * 
	 */
    private Integer state;
	/**
	 * 
	 */
    private Long shippingMethodId;
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
	/**
	 * 1,正常，0：以删除
	 */
    private Integer ruleStatus;

}
