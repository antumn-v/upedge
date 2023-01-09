package com.upedge.oms.modules.common.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class OrderErrorMessage{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private String errorMessage;
	/**
	 * 
	 */
    private String errorInfo;
	/**
	 * 
	 */
    private Date createTime;

}
