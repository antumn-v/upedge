package com.upedge.ums.modules.user.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class CustomerIoss{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private String iossNum;
	/**
	 * 
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
