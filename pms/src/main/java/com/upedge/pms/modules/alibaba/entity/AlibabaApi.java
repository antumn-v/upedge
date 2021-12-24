package com.upedge.pms.modules.alibaba.entity;

import lombok.Data;

/**
 * @author gx
 */
@Data
public class AlibabaApi{

	/**
	 * 
	 */
    private String apiKey;
	/**
	 * 
	 */
    private String apiSecret;
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
    private String userId;
	/**
	 * 
	 */
    private Long refreshTokenCreateTime;
	/**
	 * 
	 */
    private Long refreshTokenExpireTime;
	/**
	 * 
	 */
    private Long accessTokenCreateTime;
	/**
	 * 
	 */
    private Long accessTokenExpireTime;

}
