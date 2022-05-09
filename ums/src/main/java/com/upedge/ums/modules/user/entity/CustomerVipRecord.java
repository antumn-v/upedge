package com.upedge.ums.modules.user.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class CustomerVipRecord{

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
