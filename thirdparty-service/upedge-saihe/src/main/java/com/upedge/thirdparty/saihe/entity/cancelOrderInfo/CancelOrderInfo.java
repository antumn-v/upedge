package com.upedge.thirdparty.saihe.entity.cancelOrderInfo;

import javax.xml.bind.annotation.XmlElement;


public class CancelOrderInfo {

    //用户名
    String UserName;
    //密码
    String Password;
    //商户号
    Integer CustomerID;
    //订单号
    String OrderCode;
    /**
     重量不够作废	1
     未生采购单作废	2
     账号原因作废	3
     其他	4
     */
    //作废原因
    Integer OrderCancelReason;

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

    @XmlElement(name="OrderCancelReason")
    public Integer getOrderCancelReason() {
        return OrderCancelReason;
    }

    public void setOrderCancelReason(Integer orderCancelReason) {
        OrderCancelReason = orderCancelReason;
    }
}
