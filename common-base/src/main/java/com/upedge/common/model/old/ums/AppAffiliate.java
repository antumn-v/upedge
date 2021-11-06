package com.upedge.common.model.old.ums;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AppAffiliate{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 推荐人ID
	 */
    private Long referrerId;
	/**
	 * 被推荐人ID
	 */
    private Long refereeId;
	/**
	 * 提成
	 */
    private BigDecimal commission;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 来源 0:app 1:admin
	 */
    private Integer source;

}
