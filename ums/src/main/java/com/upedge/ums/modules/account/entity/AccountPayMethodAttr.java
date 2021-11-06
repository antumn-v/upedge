package com.upedge.ums.modules.account.entity;

public class AccountPayMethodAttr {
    private Integer id;

    private Integer accountPaymethodId;

    private Integer paymethodAttrId;

    private String attrKey;

    private String attrValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountPaymethodId() {
        return accountPaymethodId;
    }

    public void setAccountPaymethodId(Integer accountPaymethodId) {
        this.accountPaymethodId = accountPaymethodId;
    }

    public Integer getPaymethodAttrId() {
        return paymethodAttrId;
    }

    public void setPaymethodAttrId(Integer paymethodAttrId) {
        this.paymethodAttrId = paymethodAttrId;
    }

    public String getAttrKey() {
        return attrKey;
    }

    public void setAttrKey(String attrKey) {
        this.attrKey = attrKey == null ? null : attrKey.trim();
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue == null ? null : attrValue.trim();
    }
}