package com.upedge.thirdparty.saihe.entity.getWareHouseList;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/11.
 */
public class WhRequest {
    //用户名
    String UserName;
    //密码
    String Password;
    //客户号
    Integer Customer_ID;
    //仓库类型（默认值：1）
    Integer WareHouseType;

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
    public Integer getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(Integer customer_ID) {
        Customer_ID = customer_ID;
    }

    @XmlElement(name="WareHouseType")
    public Integer getWareHouseType() {
        return WareHouseType;
    }

    public void setWareHouseType(Integer wareHouseType) {
        WareHouseType = wareHouseType;
    }
}
