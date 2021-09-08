package com.upedge.thirdparty.saihe.response;

import com.upedge.thirdparty.saihe.entity.GetProductInventory.GetProductInventoryResult;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
public class GetProductInventoryResponse {

    @XmlAttribute(name="xmlns")
    protected String xmlns="http://tempuri.org/";

    GetProductInventoryResult getProductInventoryResult=new GetProductInventoryResult();

    public GetProductInventoryResponse(){

    }

    public GetProductInventoryResponse(String Status) {
        this.getProductInventoryResult=new GetProductInventoryResult(Status);
    }

    @XmlElement(name="GetProductInventoryResult")
    public GetProductInventoryResult getGetProductInventoryResult() {
        return getProductInventoryResult;
    }

    public void setGetProductInventoryResult(GetProductInventoryResult getProductInventoryResult) {
        this.getProductInventoryResult = getProductInventoryResult;
    }
}