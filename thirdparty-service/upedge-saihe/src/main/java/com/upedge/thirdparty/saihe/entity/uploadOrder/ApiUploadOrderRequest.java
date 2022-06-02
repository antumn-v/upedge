package com.upedge.thirdparty.saihe.entity.uploadOrder;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by cjq on 2019/1/11.
 */
public class ApiUploadOrderRequest {


    @XmlAttribute(name="xmlns")
    protected String xmlns="https://tempuri.org/";

    Request request;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

}
