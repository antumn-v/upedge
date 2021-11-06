package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class WholesaleOrder{

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
    private Long addressId;
	/**
	 * 
	 */
    private BigDecimal shipPrice;
	/**
	 * 
	 */
    private BigDecimal vatAmount;
	/**
	 * 
	 */
    private BigDecimal productAmount;
	/**
	 * 
	 */
    private BigDecimal totalWeight;
	/**
	 * 
	 */
    private BigDecimal payAmount;
	/**
	 * 
	 */
    private BigDecimal fixFee;
	/**
	 * -1=已取消。0=未支付。1=已支付。2=退款申请中。3=部分退款。4=全部退款  5=支付中
	 */
    private Integer payStatus;
	/**
	 * 0=未发货。1=已发货。
	 */
    private Integer shipStatus;
	/**
	 * 0=balance,1=paypal
	 */
    private Integer payMethod;
	/**
	 * 
	 */
    private Long toAreaId;
	/**
	 * 
	 */
    private Long shipMethodId;
	/**
	 * 
	 */
    private Long shipTemplateId;
	/**
	 * 
	 */
    private Long paymentId;
	/**
	 * 
	 */
    private Long accountFlowId;
	/**
	 * 
	 */
    private Date payTime;
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
    private String saiheOrderCode;
	/**
	 * 
	 */
    private Date trackingTime;
	/**
	 * 0=普通订单，1=excel导入
	 */
    private Integer orderType;
	/**
	 * 
	 */
    private BigDecimal dischargeAmount;

}
