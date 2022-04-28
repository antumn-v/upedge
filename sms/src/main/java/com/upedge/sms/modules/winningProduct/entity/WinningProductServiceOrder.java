package com.upedge.sms.modules.winningProduct.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gx
 */
@Data
public class WinningProductServiceOrder{

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
    private Integer serviceOption;
	/**
	 * 
	 */
    private String category;
	/**
	 * 
	 */
    private String description;
	/**
	 * 
	 */
    private String productFileLink;

	private BigDecimal payAmount;
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
    private Date payTime;
	/**
	 * 
	 */
    private Long paymentId;
	/**
	 * 
	 */
    private Integer payState;
	/**
	 * 
	 */
    private Integer refundState;
	/**
	 * 
	 */
    private Integer orderState;

}
