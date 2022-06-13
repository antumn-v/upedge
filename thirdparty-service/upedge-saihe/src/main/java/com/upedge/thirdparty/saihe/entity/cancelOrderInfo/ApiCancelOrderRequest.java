package com.upedge.thirdparty.saihe.entity.cancelOrderInfo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/6/10.
 */
public class ApiCancelOrderRequest {

    @XmlAttribute(name="xmlns")
    protected String xmlns="http://tempuri.org/";

    CancelOrderInfo cancelOrderInfo;

    @XmlElement(name="request")

    public CancelOrderInfo getCancelOrderInfo() {
        return cancelOrderInfo;
    }

    public void setCancelOrderInfo(CancelOrderInfo cancelOrderInfo) {
        this.cancelOrderInfo = cancelOrderInfo;
    }
}
