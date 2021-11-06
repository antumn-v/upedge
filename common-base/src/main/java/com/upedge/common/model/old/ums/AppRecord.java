package com.upedge.common.model.old.ums;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AppRecord{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long userId;
	/**
	 * 交易ID
	 */
    private Long relateId;
	/**
	 * 提成，按交易统计
	 */
    private BigDecimal commission;
	/**
	 * 退款=0，支付=1 现在提现=2 余额提现=3 批发退款=4
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

}
