package com.upedge.thirdparty.saihe.entity.createProcurement;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/8/26.
 */
public class ApiCreateProcurementRequest {

    @XmlAttribute(name="xmlns")
    protected String xmlns="https://tempuri.org/";

    ProcurementRequest procurementRequest;

    @XmlElement(name="request")
    public ProcurementRequest getProcurementRequest() {
        return procurementRequest;
    }

    public void setProcurementRequest(ProcurementRequest procurementRequest) {
        this.procurementRequest = procurementRequest;
    }

}
