package com.upedge.common.model.old.ums;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class UserPaypal{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long appUserId;
	/**
	 * 
	 */
    private String paypalUserId;
	/**
	 * 
	 */
    private String payerId;
	/**
	 * 
	 */
    private String payerEmail;
	/**
	 * 
	 */
    private String paypalAccount;
	/**
	 * 
	 */
    private String accessToken;
	/**
	 * 
	 */
    private String refreshToken;
	/**
	 * 
	 */
    private String tokenType;
	/**
	 * 
	 */
    private Integer expiresIn;
	/**
	 * 
	 */
    private Integer verifiedAccount;
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
