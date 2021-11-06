package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AppRefund{

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
    private Long newOrderId;
	/**
	 * 
	 */
    private Long appUserId;
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
    private String adminUserId;
	/**
	 * 
	 */
    private BigDecimal applyAmount;
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
	 * 
	 */
    private BigDecimal customerAmount;
	/**
	 * 0:申请中 1:确认退款 -1:驳货
	 */
    private Integer state;
	/**
	 * 0:app,1:admin
	 */
    private Integer source;
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

}
