package com.upedge.thirdparty.saihe.entity.getWareHouseList;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/11.
 */
public class ApiGetWareHouseResponse {

    @XmlAttribute(name="xmlns")
    protected String xmlns="http://tempuri.org/";


    GetWareHouseListResult getWareHouseListResult=new GetWareHouseListResult();

    @XmlElement(name="GetWareHouseListResult")
    public GetWareHouseListResult getGetWareHouseListResult() {
        return getWareHouseListResult;
    }

    public ApiGetWareHouseResponse() {
    }

    public ApiGetWareHouseResponse(String Status) {
        this.getWareHouseListResult=new GetWareHouseListResult(Status);
    }

    public void setGetWareHouseListResult(GetWareHouseListResult getWareHouseListResult) {
        this.getWareHouseListResult = getWareHouseListResult;
    }

}
