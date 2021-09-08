package com.upedge.thirdparty.saihe.entity.getOrderByCode;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/14.
 */
public class OrderRequest {

    String UserName;//用户名
    String Password;//密码
    Integer CustomerID;//客户号

    String OrderCode;//系统订单号
    String ClientOrderCode;//平台订单号
    String StartTime;//开始时间
    String EndTime;//结束时间
    Integer NextToken;//请求返回的NextToken
    Integer OrderSourceType;//平台类型
    //订单产品状态(All:返回全部产品,Updated:返回修改后的产品)
    String OrderListStatus;

    @XmlElement(name="UserName")
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    @XmlElement(name="Password")
    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @XmlElement(name="CustomerID")
    public Integer getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(Integer customerID) {
        CustomerID = customerID;
    }

    @XmlElement(name="OrderCode")
    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String orderCode) {
        OrderCode = orderCode;
    }

    @XmlElement(name="ClientOrderCode")
    public String getClientOrderCode() {
        return ClientOrderCode;
    }

    public void setClientOrderCode(String clientOrderCode) {
        ClientOrderCode = clientOrderCode;
    }

    @XmlElement(name="StartTime")
    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    @XmlElement(name="EndTime")
    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    @XmlElement(name="NextToken")
    public Integer getNextToken() {
        return NextToken;
    }

    public void setNextToken(Integer nextToken) {
        NextToken = nextToken;
    }

    @XmlElement(name="OrderSourceType")
    public Integer getOrderSourceType() {
        return OrderSourceType;
    }

    public void setOrderSourceType(Integer orderSourceType) {
        OrderSourceType = orderSourceType;
    }

    @XmlElement(name="OrderListStatus")
    public String getOrderListStatus() {
        return OrderListStatus;
    }

    public void setOrderListStatus(String orderListStatus) {
        OrderListStatus = orderListStatus;
    }

}
