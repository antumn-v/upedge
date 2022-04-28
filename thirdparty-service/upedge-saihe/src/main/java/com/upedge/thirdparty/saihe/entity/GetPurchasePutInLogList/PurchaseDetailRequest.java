package com.upedge.thirdparty.saihe.entity.GetPurchasePutInLogList;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by guoxing on 2020/2/21.
 */
public class PurchaseDetailRequest {


    //用户名
    private String UserName;
    //密码
    private String Password;
    //商户号
    private Integer CustomerID;
    //采购单号
    private String P_Code;
    //仓库编号
    private Integer WareHouseID;
    //系统SKU
    private String SKU;
    //自定义SKU
    private String ClientSKU;
    //请求开始时间
    private String StartTime;
    //请求结束时间
    private String EndTime;
    //NextToken
    private Integer NextToken;

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

    @XmlElement(name="P_Code")
    public String getP_Code() {
        return P_Code;
    }

    public void setP_Code(String p_Code) {
        P_Code = p_Code;
    }

    @XmlElement(name="WareHouseID")
    public Integer getWareHouseID() {
        return WareHouseID;
    }

    public void setWareHouseID(Integer wareHouseID) {
        WareHouseID = wareHouseID;
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
}
