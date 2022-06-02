package com.upedge.thirdparty.saihe.entity.getOrderByCode;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by cjq on 2019/1/14.
 */
public class ApiGetOrderRequest {

    @XmlAttribute(name="xmlns")
    protected String xmlns="http://tempuri.org/";

    OrderRequest orderRequest;

    public OrderRequest getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(OrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }

}
