package com.upedge.ums.modules.account.entity;

import java.util.Date;

/**
 * @author 海桐
 */
public class AccountPayMethod extends PaymethodDetail {
    private Integer id;

    private Long accountId;

    private Integer paymethodId;

    private String bankNum;

    private Integer isdefault;

    private Integer autopay;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }


    public Integer getPaymethodId() {
        return paymethodId;
    }

    public void setPaymethodId(Integer paymethodId) {
        this.paymethodId = paymethodId;
    }

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum == null ? null : bankNum.trim();
    }

    public Integer getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Integer isdefault) {
        this.isdefault = isdefault;
    }

    public Integer getAutopay() {
        return autopay;
    }

    public void setAutopay(Integer autopay) {
        this.autopay = autopay;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "AccountPayMethod{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", paymethodId=" + paymethodId +
                ", bankNum='" + bankNum + '\'' +
                ", isdefault=" + isdefault +
                ", autopay=" + autopay +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}