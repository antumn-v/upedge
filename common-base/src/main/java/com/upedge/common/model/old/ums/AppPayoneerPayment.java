package com.upedge.common.model.old.ums;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AppPayoneerPayment{

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
    private String paymentId;
	/**
	 * 
	 */
    private String accountId;
	/**
	 * 
	 */
    private String commitId;
	/**
	 * 
	 */
    private Integer status;
	/**
	 * 
	 */
    private String statusDescription;
	/**
	 * 
	 */
    private Double fee;
	/**
	 * 
	 */
    private Double chargedAmount;
	/**
	 * 
	 */
    private Double targetAmount;
	/**
	 * 
	 */
    private String createdAt;
	/**
	 * 
	 */
    private String lastStatus;
	/**
	 * 
	 */
    private String clientReferenceId;
	/**
	 * 
	 */
    private Double requestAmount;
	/**
	 * 
	 */
    private String requestCurrency;
	/**
	 * 
	 */
    private String sourceCurrency;
	/**
	 * 
	 */
    private String rate;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
