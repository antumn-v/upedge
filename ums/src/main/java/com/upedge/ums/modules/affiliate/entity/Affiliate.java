package com.upedge.ums.modules.affiliate.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class Affiliate{

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
	 * 被推荐人的提成
	 */
    private BigDecimal refereeCommission;
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
