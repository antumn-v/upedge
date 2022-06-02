package com.upedge.thirdparty.saihe.entity.getProducts;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/25.
 */
public class ApiGetProductResponse {

    @XmlAttribute(name="xmlns")
    protected String xmlns="http://tempuri.org/";

    GetProductsResult getProductsResult=new GetProductsResult();

    public ApiGetProductResponse(){

    }

    public ApiGetProductResponse(String Status) {
        this.getProductsResult=new GetProductsResult(Status);
    }

    @XmlElement(name="GetProductsResult")
    public GetProductsResult getGetProductsResult() {
        return getProductsResult;
    }

    public void setGetProductsResult(GetProductsResult getProductsResult) {
        this.getProductsResult = getProductsResult;
    }
}
