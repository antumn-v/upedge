package com.upedge.common.model.old.ums;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AppUserInfo{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 客户姓名
	 */
    private String customerName;
	/**
	 * 客户fb
	 */
    private String fbInfo;
	/**
	 * 客户skype
	 */
    private String skype;
	/**
	 * 客户whatsapp
	 */
    private String whatsapp;
	/**
	 * 客户wechat
	 */
    private String wechat;
	/**
	 * 创建时间
	 */
    private Date createTime;
	/**
	 * 更新时间
	 */
    private Date updateTime;

}
