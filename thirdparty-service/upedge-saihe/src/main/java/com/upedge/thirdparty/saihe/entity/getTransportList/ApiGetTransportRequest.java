package com.upedge.thirdparty.saihe.entity.getTransportList;


import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by cjq on 2019/1/11.
 */
public class ApiGetTransportRequest {

    @XmlAttribute(name="xmlns")
    protected String xmlns="https://tempuri.org/";


    TrRequest trRequest;

    public TrRequest getTrRequest() {
        return trRequest;
    }

    public void setTrRequest(TrRequest trRequest) {
        this.trRequest = trRequest;
    }


}
