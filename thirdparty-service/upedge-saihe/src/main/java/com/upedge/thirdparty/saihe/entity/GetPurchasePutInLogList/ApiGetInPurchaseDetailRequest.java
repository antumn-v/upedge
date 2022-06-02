package com.upedge.thirdparty.saihe.entity.GetPurchasePutInLogList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by guoxing on 2020/2/21.
 */
public class ApiGetInPurchaseDetailRequest {

    @XmlAttribute(name="xmlns")
    protected String xmlns="http://tempuri.org/";

    PurchaseDetailRequest purchaseDetailRequest;

    @XmlElement(name="request")
    public PurchaseDetailRequest getPurchaseDetailRequest() {
        return purchaseDetailRequest;
    }

    public void setPurchaseDetailRequest(PurchaseDetailRequest purchaseDetailRequest) {
        this.purchaseDetailRequest = purchaseDetailRequest;
    }



}
