package com.upedge.common.model.old.ums;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class UserPayoneer{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private String accessToken;
	/**
	 * 
	 */
    private Integer expiresIn;
	/**
	 * 
	 */
    private String consentedOn;
	/**
	 * 
	 */
    private String scope;
	/**
	 * 
	 */
    private String refreshToken;
	/**
	 * 
	 */
    private Integer refreshTokenExpiresIn;
	/**
	 * 
	 */
    private String idToken;
	/**
	 * 
	 */
    private String accountId;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
