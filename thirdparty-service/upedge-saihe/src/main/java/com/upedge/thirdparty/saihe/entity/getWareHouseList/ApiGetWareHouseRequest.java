package com.upedge.thirdparty.saihe.entity.getWareHouseList;


import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by cjq on 2019/1/11.
 */
public class ApiGetWareHouseRequest {

    @XmlAttribute(name="xmlns")
    protected String xmlns="http://tempuri.org/";


    WhRequest request;


    public WhRequest getRequest() {
        return request;
    }

    public void setRequest(WhRequest request) {
        this.request = request;
    }


}
