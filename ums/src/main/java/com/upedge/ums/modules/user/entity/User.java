package com.upedge.ums.modules.user.entity;

import lombok.Data;

import java.util.Date;

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
	 * 姓名
	 */
	private String username;
	/**
	 *
	 */
	private String avatar;
	/**
	 *
	 */
	private String mobile;
	/**
	 *
	 */
	private String email;
	/**
	 * 国家
	 */
	private String country;
	/**
	 *
	 */
	private String whatsapp;
	/**
	 *
	 */
	private String wechat;
	/**
	 *
	 */
	private String fbInfo;
	/**
	 *
	 */
	private String skype;
	/**
	 * 1=男，0=女
	 */
	private Integer sex;
	/**
	 * 登录次数
	 */
	private Integer loginCount;
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

	private String remark;

}
