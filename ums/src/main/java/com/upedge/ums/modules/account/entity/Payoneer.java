package com.upedge.ums.modules.account.entity;

import com.upedge.ums.modules.account.entity.payoneer.PayoneerToken;
import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class Payoneer{

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
    private String payonneerAccountId;

    private Long accountId;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

	public Payoneer(PayoneerToken payoneerToken) {
		this.accessToken = payoneerToken.getAccess_token();
		this.expiresIn = payoneerToken.getExpires_in();
		this.consentedOn = payoneerToken.getConsented_on().toString();
		this.scope = payoneerToken.getScope();
		this.refreshToken = payoneerToken.getRefresh_token();
		this.refreshTokenExpiresIn = payoneerToken.getRefresh_token_expires_in();
		this.idToken = payoneerToken.getId_token();
		this.payonneerAccountId = payoneerToken.getAccount_id();
	}

	public Payoneer() {
	}
}
