package com.upedge.thirdparty.saihe.entity.createProcurement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/8/26.
 */
public class ApiCreateProcurementResponse {

    @XmlAttribute(name="xmlns")
    protected String xmlns="http://tempuri.org/";

    CreateProcurementResult createProcurementResult=new CreateProcurementResult();

    public ApiCreateProcurementResponse(){

    }

    public ApiCreateProcurementResponse(String Status) {
        this.createProcurementResult=new CreateProcurementResult(Status);
    }

    @XmlElement(name="CreateProcurementResult")
    public CreateProcurementResult getCreateProcurementResult() {
        return createProcurementResult;
    }

    public void setCreateProcurementResult(CreateProcurementResult createProcurementResult) {
        this.createProcurementResult = createProcurementResult;
    }

}
