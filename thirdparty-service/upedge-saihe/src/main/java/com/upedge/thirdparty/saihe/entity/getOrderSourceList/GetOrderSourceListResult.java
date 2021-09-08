package com.upedge.thirdparty.saihe.entity.getOrderSourceList;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/4/9.
 */
public class GetOrderSourceListResult {

    String Status;

    OrderSourceList orderSourceList=new OrderSourceList();

    public GetOrderSourceListResult() {
    }

    public GetOrderSourceListResult(String status) {
        this.Status=status;
    }

    @XmlElement(name="Status")
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @XmlElement(name="OrderSourceList")
    public OrderSourceList getOrderSourceList() {
        return orderSourceList;
    }

    public void setOrderSourceList(OrderSourceList orderSourceList) {
        this.orderSourceList = orderSourceList;
    }
}
