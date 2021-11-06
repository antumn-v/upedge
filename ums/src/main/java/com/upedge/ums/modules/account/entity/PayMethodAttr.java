package com.upedge.ums.modules.account.entity;

/**
 * @author 海桐
 */
public class PayMethodAttr {
    private Integer id;

    private Integer paymethodId;

    /**
     * 属性名，如银行卡号
     */
    private String attrName;

    /**
     * 属性键名，如card_number
     */
    private String attrKey;

    /**
     * 是否为必填属性
     * 1=必填，0=非必填
     */
    private Integer needed;

    /**
     * 属性获取方式
     * 0=用户输入，1=api获取
     */
    private Integer obtainType;

    /**
     * 备注
     */
    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaymethodId() {
        return paymethodId;
    }

    public void setPaymethodId(Integer paymethodId) {
        this.paymethodId = paymethodId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrKey() {
        return attrKey;
    }

    public void setAttrKey(String attrKey) {
        this.attrKey = attrKey == null ? null : attrKey.trim();
    }

    public Integer getObtainType() {
        return obtainType;
    }

    public void setObtainType(Integer obtainType) {
        this.obtainType = obtainType;
    }

    public Integer getNeeded() {
        return needed;
    }

    public void setNeeded(Integer needed) {
        this.needed = needed;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}