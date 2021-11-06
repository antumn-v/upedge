package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class StoreOrderTransactionRecord{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long transactionId;
	/**
	 * 
	 */
    private Long storeOrderId;
	/**
	 * 1=sale,0=refund
	 */
    private Integer transactionType;
	/**
	 * 
	 */
    private String status;
	/**
	 * 
	 */
    private String message;
	/**
	 * 
	 */
    private Long parentId;
	/**
	 * 
	 */
    private BigDecimal storeTransactionAmount;
	/**
	 * 
	 */
    private BigDecimal usdAmount;
	/**
	 * 
	 */
    private BigDecimal usdRate;
	/**
	 * 
	 */
    private String currency;
	/**
	 * 
	 */
    private BigDecimal currencyRate;
	/**
	 * 
	 */
    private Date createdAt;
	/**
	 * 
	 */
    private Date createTime;

}
