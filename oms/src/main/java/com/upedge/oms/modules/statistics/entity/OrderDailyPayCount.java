package com.upedge.oms.modules.statistics.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class OrderDailyPayCount{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 客户ID
	 */
    private Long customerId;
	/**
	 * 客户经理ID
	 */
    private String managerCode;
	/**
	 * 订单类型  备库 = 1，普通 = 2，批发 = 3
	 */
    private Integer orderType;

    private Integer orderPayCount = 0;
	/**
	 * 运费
	 */
    private BigDecimal shipPrice = BigDecimal.ZERO;
	/**
	 * 服务费
	 */
	private BigDecimal serviceFee = BigDecimal.ZERO;
	/**
	 * 产品费
	 */
    private BigDecimal productAmount = BigDecimal.ZERO;
	/**
	 * 库存抵扣金额
	 */
    private BigDecimal productDischargeAmount = BigDecimal.ZERO;
	/**
	 * paypal手续费
	 */
    private BigDecimal fixFee = BigDecimal.ZERO;
	/**
	 * 
	 */
    private BigDecimal vatAmount = BigDecimal.ZERO;
	/**
	 * 支付金额：product_amount + ship_price + vat + fix_fee - discharge
	 */
    private BigDecimal payAmount = BigDecimal.ZERO;
	/**
	 * 入账金额：product_amount + ship_price + vat
	 */
    private BigDecimal creditAmount = BigDecimal.ZERO;
	/**
	 * 支付时间，每天统计一次
	 */
    private Date payTime;

    private Integer refundCount = 0;

    private BigDecimal refundAmount = BigDecimal.ZERO;

	private Integer shippedRefundCount = 0;

	private BigDecimal shippedRefundAmount = BigDecimal.ZERO;

	private Integer unShippedRefundCount = 0;

	private BigDecimal unShippedRefundAmount = BigDecimal.ZERO;

	private BigDecimal benefitPayAmount = BigDecimal.ZERO;

	public OrderDailyPayCount() {
	}

	public OrderDailyPayCount(BigDecimal productAmount, BigDecimal fixFee, BigDecimal shipPrice,BigDecimal serviceFee, BigDecimal vatAmount, BigDecimal dischargeAmount, Date payTime, Long customerId, Integer orderType ) {
		this.productAmount = productAmount;
		this.fixFee = fixFee;
//		this.payAmount = productAmount.add(fixFee).add(shipPrice).add(vatAmount).subtract(dischargeAmount);
		this.payAmount = productAmount.add(shipPrice).add(vatAmount).add(serviceFee).subtract(dischargeAmount);
		this.creditAmount = productAmount.add(shipPrice).add(vatAmount);
		this.shipPrice = shipPrice;
		this.serviceFee = serviceFee;
		this.productDischargeAmount = dischargeAmount;
		this.vatAmount = vatAmount;
		this.customerId = customerId;
		this.orderType = orderType;
		this.payTime = payTime;
	}

	public OrderDailyPayCount(BigDecimal productAmount, BigDecimal fixFee, Date payTime, Long customerId, Integer orderType) {
		this.productAmount = productAmount;
		this.fixFee = fixFee;
		this.payAmount = productAmount.add(fixFee);
		this.creditAmount = productAmount;
		this.shipPrice = BigDecimal.ZERO;
		this.productDischargeAmount = BigDecimal.ZERO;
		this.vatAmount = BigDecimal.ZERO;
		this.customerId = customerId;
		this.orderType = orderType;
		this.payTime = payTime;
	}

}
