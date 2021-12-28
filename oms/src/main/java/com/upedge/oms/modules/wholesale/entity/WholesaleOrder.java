package com.upedge.oms.modules.wholesale.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
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
    private Long customerId;
    private Long customerLoginName;
	/**
	 * 
	 */
    private BigDecimal payAmount;
	/**
	 * 
	 */
    private String shipMethodName;
	/**
	 * 
	 */
    private BigDecimal shipPrice;// = BigDecimal.ZERO;

	private BigDecimal serviceFee;

	private Integer freightReview;
	/**
	 * 
	 */
    private BigDecimal totalWeight;
	private BigDecimal volumeWeight;
	/**
	 * 
	 */
    private BigDecimal productAmount;
	/**
	 * 
	 */
    private BigDecimal vatAmount;// = BigDecimal.ZERO;
	/**
	 * 
	 */
    private BigDecimal productDischargeAmount;// = BigDecimal.ZERO;
	/**
	 * 
	 */
    private BigDecimal fixFee;// = BigDecimal.ZERO;
	/**
	 * 0=balance,1=paypal
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
	 * -1=已取消。0=未支付。1=已支付。2=退款申请中。3=部分退款。4=全部退款  5=支付中
	 */
    private Integer payState;
	/**
	 * 
	 */
    private Integer refundState;
	/**
	 * 0=未发货。1=已发货。
	 */
    private Integer shipState;
	/**
	 * 
	 */
    private Long toAreaId;
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
	 *   正常 = 0,待审查 = 1,作废 = 2
	 */
    private Integer orderStatus;
	/**
	 * 0=普通订单，1=excel导入
	 */
    private Integer orderType;

    private String managerCode;

	private String trackingCode;

    public void initOrder(WholesaleOrder order){
		order.setPayState(0);
		order.setRefundState(0);
		order.setShipState(0);
		order.setVatAmount(BigDecimal.ZERO);
		order.setFreightReview(0);
		order.setOrderType(0);
		order.setOrderStatus(0);
		order.setProductDischargeAmount(BigDecimal.ZERO);
		order.setServiceFee(BigDecimal.ZERO);
		order.setShipPrice(BigDecimal.ZERO);
    }

}
