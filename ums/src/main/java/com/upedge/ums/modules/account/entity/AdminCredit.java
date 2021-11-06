package com.upedge.ums.modules.account.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class AdminCredit{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 申请原因
	 */
    private String reason;
	/**
	 * 客户id
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
	 * 0:申请中 1:已确认 -1:已驳回
	 */
    private Integer state;
	/**
	 * admin申请人id
	 */
    private String applyUserId;
	/**
	 * admin执行人id
	 */
    private Long executeUserId;
	/**
	 * 申请额度上限
	 */
    private BigDecimal applyCredit;
	/**
	 * 确认时的之前额度上限
	 */
    private BigDecimal currCredit;

	private Integer gteState;


}
