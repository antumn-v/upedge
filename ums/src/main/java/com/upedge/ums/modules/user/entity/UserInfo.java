package com.upedge.ums.modules.user.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class UserInfo{

	/**
	 * 与userid一致
	 */
    private Long id;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private Long orgId;
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
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
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

}
