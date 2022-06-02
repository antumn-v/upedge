package com.upedge.thirdparty.saihe.entity.getOrderSourceList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/4/8.
 */
public class ApiGetOrderSourceResponse {


    @XmlAttribute(name="xmlns")
    protected String xmlns="https://tempuri.org/";

    private GetOrderSourceListResult getOrderSourceListResult;

    public ApiGetOrderSourceResponse() {
    }

    public ApiGetOrderSourceResponse(String Status) {
         this.getOrderSourceListResult=new GetOrderSourceListResult(Status);
    }

    @XmlElement(name="GetOrderSourceListResult")
    public GetOrderSourceListResult getGetOrderSourceListResult() {
        return getOrderSourceListResult;
    }

    public void setGetOrderSourceListResult(GetOrderSourceListResult getOrderSourceListResult) {
        this.getOrderSourceListResult = getOrderSourceListResult;
    }
}
