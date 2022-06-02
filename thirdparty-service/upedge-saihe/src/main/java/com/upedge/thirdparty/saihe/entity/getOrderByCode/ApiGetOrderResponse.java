package com.upedge.thirdparty.saihe.entity.getOrderByCode;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/14.
 */
public class ApiGetOrderResponse {
    @XmlAttribute(name="xmlns")
    protected String xmlns="https://tempuri.org/";


    private GetOrdersResult getOrdersResult;

    public ApiGetOrderResponse() {
    }

    public ApiGetOrderResponse(String Status) {
        this.getOrdersResult=new GetOrdersResult(Status);
    }

    @XmlElement(name="GetOrdersResult")
    public GetOrdersResult getGetOrdersResult() {
        return getOrdersResult;
    }

    public void setGetOrdersResult(GetOrdersResult getOrdersResult) {
        this.getOrdersResult = getOrdersResult;
    }

}
