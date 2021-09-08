package com.upedge.thirdparty.saihe.entity.getOrderByCode;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/14.
 */
public class GetOrdersResult {

    String Status;
    String Msg;
    Integer NextToken;

    Boolean IsSetNextToken;
    Boolean IsSetOrders;

    OrderInfoList orderInfoList=new OrderInfoList();

    public GetOrdersResult() {
    }
    public GetOrdersResult(String status) {
        Status = status;
    }

    @XmlElement(name="IsSetNextToken")
    public Boolean getSetNextToken() {
        return IsSetNextToken;
    }

    public void setSetNextToken(Boolean setNextToken) {
        IsSetNextToken = setNextToken;
    }

    @XmlElement(name="IsSetOrders")
    public Boolean getSetOrders() {
        return IsSetOrders;
    }

    public void setSetOrders(Boolean setOrders) {
        IsSetOrders = setOrders;
    }
    @XmlElement(name="Msg")
    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    @XmlElement(name="Status")
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @XmlElement(name="NextToken")
    public Integer getNextToken() {
        return NextToken;
    }

    public void setNextToken(Integer nextToken) {
        NextToken = nextToken;
    }

    @XmlElement(name="OrderInfoList")
    public OrderInfoList getOrderInfoList() {
        return orderInfoList;
    }

    public void setOrderInfoList(OrderInfoList orderInfoList) {
        this.orderInfoList = orderInfoList;
    }
}
