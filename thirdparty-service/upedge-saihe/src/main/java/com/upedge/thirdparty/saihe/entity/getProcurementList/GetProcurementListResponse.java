package com.upedge.thirdparty.saihe.entity.getProcurementList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by guoxing on 2020/7/21.
 */
public class GetProcurementListResponse {

    @XmlAttribute(name="xmlns")
    protected String xmlns="http://tempuri.org/";

    GetProcurementListResult getProcurementListResult;

    public GetProcurementListResponse(){

    }

    public GetProcurementListResponse(String Status) {
        this.getProcurementListResult=new GetProcurementListResult(Status);
    }

    @XmlElement(name="GetProcurementListResult")
    public GetProcurementListResult getGetProcurementListResult() {
        return getProcurementListResult;
    }

    public void setGetProcurementListResult(GetProcurementListResult getProcurementListResult) {
        this.getProcurementListResult = getProcurementListResult;
    }

}
