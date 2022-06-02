package com.upedge.thirdparty.saihe.entity.getProducts;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/25.
 */
public class ApiGetProductRequest {

    @XmlAttribute(name="xmlns")
    protected String xmlns="http://tempuri.org/";

    ProductRequest productRequest;

    @XmlElement(name="productRequest")
    public ProductRequest getProductRequest() {
        return productRequest;
    }

    public void setProductRequest(ProductRequest productRequest) {
        this.productRequest = productRequest;
    }

}
