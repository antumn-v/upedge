package com.upedge.ums.modules.affiliate.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class AffiliateCodeRecord{

	/**
	 * 
	 */
    private String affiliateCode;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private Date createTime;

}
