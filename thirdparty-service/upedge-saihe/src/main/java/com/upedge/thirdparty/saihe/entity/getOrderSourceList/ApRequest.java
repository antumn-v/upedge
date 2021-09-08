package com.upedge.thirdparty.saihe.entity.getOrderSourceList;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/4/9.
 */
public class ApRequest {

    String UserName;
    String Password;
    Integer CustomerID;
    Integer OrderSourceType;//来源渠道类型   B2C网站平台 = 4,

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

    @XmlElement(name="OrderSourceType")
    public Integer getOrderSourceType() {
        return OrderSourceType;
    }

    public void setOrderSourceType(Integer orderSourceType) {
        OrderSourceType = orderSourceType;
    }
}
