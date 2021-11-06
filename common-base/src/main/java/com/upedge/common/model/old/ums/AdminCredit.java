package com.upedge.common.model.old.ums;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
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
    private String executeUserId;
	/**
	 * 申请额度上限
	 */
    private BigDecimal applyCredit;
	/**
	 * 确认时的之前额度上限
	 */
    private BigDecimal currCredit;

}
