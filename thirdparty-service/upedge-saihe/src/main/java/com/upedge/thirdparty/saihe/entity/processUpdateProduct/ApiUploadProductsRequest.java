package com.upedge.thirdparty.saihe.entity.processUpdateProduct;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/23.
 */
public class ApiUploadProductsRequest {

    @XmlAttribute(name="xmlns")
    protected String xmlns="https://tempuri.org/";

    Prequest request;


    @XmlElement(name="request")
    public Prequest getRequest() {
        return request;
    }

    public void setRequest(Prequest request) {
        this.request = request;
    }

}
