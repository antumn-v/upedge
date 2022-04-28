package com.upedge.thirdparty.saihe.entity.GetPurchasePutInLogList;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by guoxing on 2020/2/21.
 */
public class GetPurchasePutInLogListResult {

    //分页标识
    Integer NextToken;

    //是否设置分页标识
    boolean IsSetNextToken;

    boolean IsSetPurchaseInDetailList;

    String Status;

    PurchaseInDetailList purchaseInDetailList=new PurchaseInDetailList();

    public GetPurchasePutInLogListResult() {

    }

    public GetPurchasePutInLogListResult(String status) {
        this.Status=Status;
    }

    @XmlElement(name="NextToken")
    public Integer getNextToken() {
        return NextToken;
    }

    public void setNextToken(Integer nextToken) {
        NextToken = nextToken;
    }

    @XmlElement(name="IsSetNextToken")
    public boolean isSetNextToken() {
        return IsSetNextToken;
    }

    public void setSetNextToken(boolean setNextToken) {
        IsSetNextToken = setNextToken;
    }

    @XmlElement(name="IsSetPurchaseInDetailList")
    public boolean isSetPurchaseInDetailList() {
        return IsSetPurchaseInDetailList;
    }

    public void setSetPurchaseInDetailList(boolean setPurchaseInDetailList) {
        IsSetPurchaseInDetailList = setPurchaseInDetailList;
    }

    @XmlElement(name="PurchaseInDetailList")
    public PurchaseInDetailList getPurchaseInDetailList() {
        return purchaseInDetailList;
    }

    public void setPurchaseInDetailList(PurchaseInDetailList purchaseInDetailList) {
        this.purchaseInDetailList = purchaseInDetailList;
    }

    @XmlElement(name="Status")
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
