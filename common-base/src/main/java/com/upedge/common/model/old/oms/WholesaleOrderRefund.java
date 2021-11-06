package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class WholesaleOrderRefund{

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
    private String adminUserId;
	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 
	 */
    private BigDecimal refundAmount;
	/**
	 * 申请退款的理由
	 */
    private String applyInfo;
	/**
	 * 驳回理由
	 */
    private String rejectInfo;
	/**
	 * 0=审核中，1=已退款，2=已驳回
	 */
    private Integer state;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 退款订单发货状态 0:未发货 1:已发货
	 */
    private Integer trackingState;
	/**
	 * 赛盒订单渠道
	 */
    private Integer orderSourceId;

}
