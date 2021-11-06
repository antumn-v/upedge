package com.upedge.ums.modules.log.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author author
 */
@Data
public class UserOpLog implements Serializable {

	/**
	 *
	 */
	private Long id;
	/**
	 * 操作类型 1:查询 2:修改
	 */
	private String module;

	private Integer opType;
	/**
	 *
	 */
	private String opName;
	/**
	 *
	 */
	private Long customerId;
	/**
	 *
	 */
	private Long userId;
	/**
	 *
	 */
	private String loginName;
	/**
	 *
	 */
	private String url;
	/**
	 *
	 */
	private String opReq;
	/**
	 * 1:成功 0:异常
	 */
	private Integer result;
	/**
	 *
	 */
	private String ip;
	/**
	 * 创建时间
	 */
	private Date createTime;



}
