package com.upedge.ums.modules.user.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class CustomerVipRecord{

	//授权
	public static final int VIP_AUTHORIZE = 1;
	//撤销
	public static final int VIP_REVOKE = 0;

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 1=授权  0=撤销
	 */
    private Integer vipType;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Long managerId;

}
