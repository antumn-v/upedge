package com.upedge.thirdparty.saihe.entity.processUpdateProduct;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/23.
 */
public class ApiUploadProductsResponse {

    @XmlAttribute(name="xmlns")
    protected String xmlns="http://tempuri.org/";

    private ProcessUpdateProductResult processUpdateProductResult;

    public ApiUploadProductsResponse() {
    }

    public ApiUploadProductsResponse(String Status) {
        this.processUpdateProductResult=new ProcessUpdateProductResult(Status);
    }

    @XmlElement(name="ProcessUpdateProductResult")
    public ProcessUpdateProductResult getProcessUpdateProductResult() {
        return processUpdateProductResult;
    }

    public void setProcessUpdateProductResult(ProcessUpdateProductResult processUpdateProductResult) {
        this.processUpdateProductResult = processUpdateProductResult;
    }

}
