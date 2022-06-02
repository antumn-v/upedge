package com.upedge.thirdparty.saihe.entity.getProcurementList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by guoxing on 2020/7/21.
 */
public class GetProcurementListRequest {

    @XmlAttribute(name="xmlns")
    protected String xmlns="https://tempuri.org/";

    private GetProcurementList getProcurementList;

    @XmlElement(name="request")
    public GetProcurementList getGetProcurementList() {
        return getProcurementList;
    }

    public void setGetProcurementList(GetProcurementList getProcurementList) {
        this.getProcurementList = getProcurementList;
    }

}
