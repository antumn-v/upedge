package com.upedge.ums.modules.account.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class RefundRecord{

	/**
	 * 
	 */
    private Integer id;
	/**
	 *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
	 */
    private Long rechargeRecordId;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 
	 */
    private Integer orderType;
	/**
	 * 
	 */
    private BigDecimal amount;
	/**
	 * 0=balance 1=rebate
	 */
    private Integer source;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 每个订单对应扣款记录的退款顺序
	 */
    private Integer seq;

}
