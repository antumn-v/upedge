package com.upedge.thirdparty.saihe.entity.getOrderSourceList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/4/8.
 */
public class ApiGetOrderSourceRequest {


    @XmlAttribute(name="xmlns")
    protected String xmlns="http://tempuri.org/";

    private ApRequest apRequest;

    @XmlElement(name="apRequest")
    public ApRequest getApRequest() {
        return apRequest;
    }

    public void setApRequest(ApRequest apRequest) {
        this.apRequest = apRequest;
    }

}
