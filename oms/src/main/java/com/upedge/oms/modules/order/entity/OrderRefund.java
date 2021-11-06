package com.upedge.oms.modules.order.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class OrderRefund{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private String reason;
	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 驳回理由
	 */
    private String rejectInfo;
	/**
	 * 
	 */
    private String managerCode;
	/**
	 * 
	 */
    private BigDecimal refundAmount;
	/**
	 * 
	 */
    private BigDecimal refundVatAmount;
	/**
	 * 
	 */
    private BigDecimal refundProductAmount;
	/**
	 * 
	 */
    private BigDecimal refundShippingPrice;
	/**
	 * 0:申请中 1:确认退款 -1:驳货
	 */
    private Integer state;
	/**
	 * 0:app,1:admin
	 */
    private Long source;
	/**
	 * 退款订单发货状态 0:未发货 1:已发货
	 */
    private Integer trackingState;
	/**
	 * 赛盒订单渠道
	 */
    private Integer orderSourceId;
	/**
	 * 备注
	 */
    private String remark;

	private Integer gteState;

}
