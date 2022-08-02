package com.upedge.oms.modules.order.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class Order{

	public static Integer QUOTE_UNQUOTED = 0;//未报价
	public static Integer QUOTE_QUOTING = 1;//报价中
	public static Integer QUOTE_PARTIAL = 2;//部分报价
	public static Integer QUOTE_QUOTED = 3;//全部报价

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
    private Long storeId;

    private Long orgId;

    private String orgPath;
	/**
	 * 
	 */
    private BigDecimal payAmount;
	/**
	 * 
	 */
    private Long shipMethodId;
	/**
	 * 
	 */
    private BigDecimal shipPrice;

	private BigDecimal serviceFee;
	/**
	 * 
	 */
    private BigDecimal totalWeight;
	/**
	 * 
	 */
    private BigDecimal productAmount;

    private BigDecimal cnyProductAmount;
	/**
	 * 
	 */
    private BigDecimal productDischargeAmount;
	/**
	 * 
	 */
    private BigDecimal fixFee;
	/**
	 * 手续费百分比
	 */
    private BigDecimal fixFeePercentage;
	/**
	 * 
	 */
    private Integer payMethod;
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
    private Integer shipState;
	/**
	 * 0:未报价，1=报价中，2=部分报价，3=全部报价
	 */
    private Integer quoteState;
	/**
	 * 订单类型  正常订单=0 补发订单=1 拆分订单=2 合并订单=3
	 */
    private Integer orderType;

    private Long toAreaId;
	/**
	 * 支付时美元对人民币汇率
	 */
    private BigDecimal cnyRate;

	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

    private String saiheOrderCode;

    private BigDecimal vatAmount;

    private Integer orderStatus;

    private String managerCode;

	private String shippingWarehouse;

	private String trackingCode;

	private Integer stockState;

	public Order() {
	}

	public void initOrder(){
		this.payState = 0;
		this.shipState = 0;
		this.refundState = 0;
		this.payAmount = BigDecimal.ZERO;
		this.shipPrice = BigDecimal.ZERO;
		this.serviceFee = BigDecimal.ZERO;
		this.productDischargeAmount = BigDecimal.ZERO;
		this.cnyProductAmount = BigDecimal.ZERO;
		this.vatAmount = BigDecimal.ZERO;
		this.orderStatus = 0;
		this.fixFee = BigDecimal.ZERO;
		this.createTime = new Date();
		this.updateTime = this.createTime;
		this.payTime = null;
		this.payMethod = null;
		this.paymentId = null;
		this.saiheOrderCode = null;
		this.shippingWarehouse = null;
		this.shipMethodId = null;
	}
}
