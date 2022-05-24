package com.upedge.ums.modules.manager.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class CustomerManager{

	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 用户经理code
	 */
    private String managerCode;
	/**
	 * 
	 */
    private Date createTime;

}
