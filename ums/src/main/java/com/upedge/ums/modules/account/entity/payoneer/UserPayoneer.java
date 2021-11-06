package com.upedge.ums.modules.account.entity.payoneer;

import java.util.Date;

public class UserPayoneer {
    private Integer id;

    private Long userId;

    private String accessToken;

    private Integer expiresIn;

    private int consentedOn;

    private String scope;

    private String refreshToken;

    private Integer refreshTokenExpiresIn;

    private String idToken;

    private String accountId;

    private Date createTime;

    private Date updateTime;

    public UserPayoneer(PayoneerToken token) {
        this.accessToken = token.getAccess_token();
        this.consentedOn = token.getConsented_on();
        this.expiresIn = token.getExpires_in();
        this.refreshToken = token.getRefresh_token();
        this.refreshTokenExpiresIn = token.getRefresh_token_expires_in();
        this.scope = token.getScope();
        this.idToken = token.getId_token();
        this.accountId = token.getAccount_id();
    }

    public UserPayoneer() {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public int getConsentedOn() {
        return consentedOn;
    }

    public void setConsentedOn(int consentedOn) {
        this.consentedOn = consentedOn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope == null ? null : scope.trim();
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken == null ? null : refreshToken.trim();
    }

    public Integer getRefreshTokenExpiresIn() {
        return refreshTokenExpiresIn;
    }

    public void setRefreshTokenExpiresIn(Integer refreshTokenExpiresIn) {
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken == null ? null : idToken.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}