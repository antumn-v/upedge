package com.upedge.thirdparty.saihe.entity.getTransportList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/11.
 */
public class ApiGetTransportResponse {

    @XmlAttribute(name="xmlns")
    protected String xmlns="http://tempuri.org/";

    private GetTransportListResult getTransportListResult;


    @XmlElement(name="GetTransportListResult")
    public GetTransportListResult getGetTransportListResult() {
        return getTransportListResult;
    }

    public ApiGetTransportResponse(String Status) {
        this.getTransportListResult=new GetTransportListResult(Status);
    }

    public ApiGetTransportResponse() {
    }

    public void setGetTransportListResult(GetTransportListResult getTransportListResult) {
        this.getTransportListResult = getTransportListResult;
    }

}
