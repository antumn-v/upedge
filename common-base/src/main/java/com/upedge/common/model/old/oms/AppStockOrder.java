package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AppStockOrder{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 仓库ID
	 */
    private Long warehouseId;
	/**
	 * 用户ID
	 */
    private Long userId;
	/**
	 * 支付方式,balance = 0,paypal=1
	 */
    private Integer payMethod;
	/**
	 * 交易ID
	 */
    private Long paymentId;
	/**
	 * 交易金额
	 */
    private BigDecimal amount;
	/**
	 * paypal手续费
	 */
    private BigDecimal paypalFee;
	/**
	 * 订单状态,待支付=0，已支付=1，申请退款=2，部分退款=3，全部退款=4，取消订单=-1
	 */
    private Integer state;
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
    private Integer adminState;
	/**
	 * 所属客户经理
	 */
    private String userManager;
	/**
	 * 赛盒备库订单号
	 */
    private String saiheCode;

}
