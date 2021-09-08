package com.upedge.thirdparty.saihe.entity.getPackages;


import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by cjq on 2019/5/15.
 */
public class ApiGetPackagesRequest {

    @XmlAttribute(name="xmlns")
    protected String xmlns="http://tempuri.org/";

    PackagesRequest request;

    public PackagesRequest getRequest() {
        return request;
    }

    public void setRequest(PackagesRequest request) {
        this.request = request;
    }

}
