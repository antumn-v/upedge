package com.upedge.oms.modules.order.entity;

import com.upedge.common.model.store.StoreVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class Order{

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
    private BigDecimal productDischargeAmount = BigDecimal.ZERO;
	/**
	 * 
	 */
    private BigDecimal fixFee = BigDecimal.ZERO;
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
    private Integer payState = 0;
	/**
	 * 
	 */
    private Integer refundState = 0;
	/**
	 * 
	 */
    private Integer shipState = 0;


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

    private BigDecimal vatAmount = BigDecimal.ZERO;

    private Integer orderStatus;

    private String managerCode;


	public Order() {
	}
}
