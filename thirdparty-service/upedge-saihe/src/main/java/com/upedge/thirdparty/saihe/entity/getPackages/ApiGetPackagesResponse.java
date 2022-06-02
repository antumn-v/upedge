package com.upedge.thirdparty.saihe.entity.getPackages;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/5/15.
 */
public class ApiGetPackagesResponse {

    @XmlAttribute(name="xmlns")
    protected String xmlns="https://tempuri.org/";

    GetPackagesResult getPackagesResult=new GetPackagesResult();

    public ApiGetPackagesResponse() {
    }

    public ApiGetPackagesResponse(String Status) {
        this.getPackagesResult=new GetPackagesResult(Status);
    }

    @XmlElement(name="GetPackagesResult")
    public GetPackagesResult getGetPackagesResult() {
        return getPackagesResult;
    }

    public void setGetPackagesResult(GetPackagesResult getPackagesResult) {
        this.getPackagesResult = getPackagesResult;
    }

}
