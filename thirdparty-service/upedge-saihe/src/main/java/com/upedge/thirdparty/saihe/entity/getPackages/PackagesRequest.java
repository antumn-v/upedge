package com.upedge.thirdparty.saihe.entity.getPackages;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

/**
 * Created by cjq on 2019/5/15.
 */
public class PackagesRequest {

    //用户名
    String UserName;
    //密码
    String Password;
    //客户号
    Integer CustomerID;
    //包裹单号
    Integer PackageID;
    //发货时间开始
    Date ShipTimeBegin;
    //发货时间结束
    Date ShipTimeEnd;
    //渠道类型
    Integer OrderSourceType;
    //分页标识
    Integer NextToken;

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

    @XmlElement(name="PackageID")
    public Integer getPackageID() {
        return PackageID;
    }

    public void setPackageID(Integer packageID) {
        PackageID = packageID;
    }

    @XmlElement(name="ShipTimeBegin")
    public Date  getShipTimeBegin() {
        return ShipTimeBegin;
    }

    public void setShipTimeBegin(Date shipTimeBegin) {
        ShipTimeBegin = shipTimeBegin;
    }

    @XmlElement(name="ShipTimeEnd")
    public Date getShipTimeEnd() {
        return ShipTimeEnd;
    }

    public void setShipTimeEnd(Date shipTimeEnd) {
        ShipTimeEnd = shipTimeEnd;
    }

    @XmlElement(name="OrderSourceType")
    public Integer getOrderSourceType() {
        return OrderSourceType;
    }

    public void setOrderSourceType(Integer orderSourceType) {
        OrderSourceType = orderSourceType;
    }

    @XmlElement(name="NextToken")
    public Integer getNextToken() {
        return NextToken;
    }

    public void setNextToken(Integer nextToken) {
        NextToken = nextToken;
    }
}
