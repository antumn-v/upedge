package com.upedge.oms.modules.stock.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class StockOrder{

	public Integer UN_PURCHASE = 0;
	public Integer PURCHASING = 1;
	public Integer PART_PURCHASE = 2;
	public Integer PURCHASED = 3;

	/**
	 * 
	 */
    private Long id;
	/**
	 * 仓库ID
	 */
    private String warehouseCode;
	/**
	 * 用户ID
	 */
    private Long customerId;
	/**
	 * 支付方式,balance = 0,paypal=1
	 */
    private Integer payMethod;
	/**
	 * 交易ID
	 */
    private Long paymentId;

	private BigDecimal productAmount;

	private BigDecimal shipPrice;

	private String shipMethod;

	private Integer shipReview;
	/**
	 * 交易金额
	 */
    private BigDecimal amount;
	/**
	 * paypal手续费
	 */
    private BigDecimal paypalFee;
	/**
	 * 支付状态,待支付=0，已支付=1，取消订单=-1,支付中=2
	 */
    private Integer payState;
	/**
	 * 退款状态:0=未退款，1=申请中，2=驳回，3=部分退款，4=全部退款
	 */
    private Integer refundState;
	/**
	 * 支付时间
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
	 * 后台状态 0:未导入赛盒 1:已导入赛盒
	 */
	private Integer saiheState;
	/**
	 * 赛盒备库订单号
	 */
	private String saiheCode;
	/**
	 * 客户经理
	 */
	private String managerCode;
	/**
	 * 组织路径
	 */
	private String orgPath;
	/**
	 * 采购状态，0=未采购，1=采购中，2=部分采购，3=采购完成
	 */
	private Integer purchaseState;



}
