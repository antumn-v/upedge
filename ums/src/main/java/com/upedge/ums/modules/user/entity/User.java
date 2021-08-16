package com.upedge.ums.modules.user.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class User{

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
    private String loginName;
	/**
	 * 
	 */
    private String loginPass;
	/**
	 * 0 超级管理员 1管理员 2普通用户
	 */
    private Integer userType;
	/**
	 * 1 正常 0 停用
	 */
    private Integer status;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 最近登录时间
	 */
    private Date lastLoginTime;
	/**
	 * 登录次数
	 */
    private Integer loginCount;

}
