package com.upedge.thirdparty.saihe.entity.GetProductInventory;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

/**
 * Created by jiaqi on 2019/9/9.
 */
public class InventoryRequest {

    Integer CustomerID;//商户号

    String UserName;//用户名

    String Password;//密码

    Date StartTime;//开始时间

    Date EndTime;//结束时间

    String SKU;//产品SKU

    String ClientSKU;//自定义SKU

    Integer WarehouseID;//仓库ID

    Integer NextToken;//分页标识

    @XmlElement(name="CustomerID")
    public Integer getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(Integer customerID) {
        CustomerID = customerID;
    }

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

    @XmlElement(name="StartTime")
    public Date getStartTime() {
        return StartTime;
    }

    public void setStartTime(Date startTime) {
        StartTime = startTime;
    }

    @XmlElement(name="EndTime")
    public Date getEndTime() {
        return EndTime;
    }

    public void setEndTime(Date endTime) {
        EndTime = endTime;
    }

    @XmlElement(name="SKU")
    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    @XmlElement(name="ClientSKU")
    public String getClientSKU() {
        return ClientSKU;
    }

    public void setClientSKU(String clientSKU) {
        ClientSKU = clientSKU;
    }

    @XmlElement(name="WarehouseID")
    public Integer getWarehouseID() {
        return WarehouseID;
    }

    public void setWarehouseID(Integer warehouseID) {
        WarehouseID = warehouseID;
    }

    @XmlElement(name="NextToken")
    public Integer getNextToken() {
        return NextToken;
    }

    public void setNextToken(Integer nextToken) {
        NextToken = nextToken;
    }
}
