package com.upedge.common.model.old.ums;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class UserAliexpress{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long userId;
	/**
	 * 
	 */
    private String code;
	/**
	 * 
	 */
    private String accessToken;
	/**
	 * 
	 */
    private Date expireTime;
	/**
	 * 
	 */
    private String aeId;
	/**
	 * 
	 */
    private String refreshToken;
	/**
	 * 
	 */
    private String aeNick;
	/**
	 * 
	 */
    private Integer source;
	/**
	 * 
	 */
    private String locale;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
