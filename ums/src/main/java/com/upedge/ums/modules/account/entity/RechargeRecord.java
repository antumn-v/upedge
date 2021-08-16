package com.upedge.ums.modules.account.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class RechargeRecord{

	/**
	 * 
	 */
    private Integer id;
	/**
	 *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
	 */
    private Long rechargeId;
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
	 * 0=balance 1=rebate 2=credit
	 */
    private Integer source;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 每个订单对应充值记录的扣款顺序
	 */
    private Integer seq;

}
