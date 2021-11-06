package com.upedge.oms.modules.statistics.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class OrderDailyRefundCount {

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

	/**
	 * 在这里指退款时间
	 */
	private Date payTime;

	/**
	 * 退款订单数  一个refunid  只是一个订单
	 */
	private Integer refundCount = 1;

	/**
	 * 退款总额 refund_amount
	 */
	private BigDecimal refundAmount= BigDecimal.ZERO;

	/**
	 * 已发货的订单退款数 shiped_refund_count
	 */
	private Integer shippedRefundCount=0;

	/**
	 * 已发货的订单退款总额 shiped_refund_amount
	 */
	private BigDecimal shippedRefundAmount= BigDecimal.ZERO;

	/**
	 * 未发货订单退款数 un_shipped_refund_count
	 */
	private Integer unShippedRefundCount=0;

	/**
	 *  未发货订单退款总额 un_shipped_refund_amount
	 */
	private BigDecimal unShippedRefundAmount = BigDecimal.ZERO;


	public OrderDailyRefundCount() {
	}



}
