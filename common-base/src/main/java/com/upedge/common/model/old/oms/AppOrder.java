package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AppOrder{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private String name;
	/**
	 * 
	 */
    private String email;
	/**
	 * 
	 */
    private Date createdAt;
	/**
	 * 
	 */
    private Date createAtGmt;
	/**
	 * 
	 */
    private Date updatedAt;
	/**
	 * 
	 */
    private Date updateAtGmt;
	/**
	 * 
	 */
    private String test;
	/**
	 * 
	 */
    private BigDecimal totalWeight;
	/**
	 * 
	 */
    private String currency;
	/**
	 * 
	 */
    private Integer buyerAcceptsMarketing;
	/**
	 * 
	 */
    private String phone;
	/**
	 * 订单运费
	 */
    private Double freight;
	/**
	 * 
	 */
    private BigDecimal totalLineItemsPrice;
	/**
	 * 
	 */
    private Double totalPrice;
	/**
	 * 
	 */
    private String customerLocale;
	/**
	 * 
	 */
    private Long addressId;
	/**
	 * 收件人姓名
	 */
    private String customerName;
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
    private Long userId;
	/**
	 * 
	 */
    private String shopifyStatus;
	/**
	 * 
	 */
    private String fulfillmentStatus;
	/**
	 * 
	 */
    private Date processedAt;
	/**
	 * 
	 */
    private String gateway;
	/**
	 * 
	 */
    private String note;
	/**
	 * 
	 */
    private BigDecimal usdRate;
	/**
	 * 
	 */
    private BigDecimal currencyRate;
	/**
	 * 
	 */
    private String countryName;

}
