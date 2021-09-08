package com.upedge.thirdparty.saihe.entity.getProducts;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/25.
 */
public class ProductRequest {

    String UserName	;//		必填	用户名
    String Password;//必填	密码
    Integer CustomerID;//必填	客户号
    String SKU;//选填	产品SKU
    String ProductGroupSKU;//选填	产品母体ID
    String StartTime;//与SKU二选一	请求添加开始时间
    String EndTime;//请求添加结束时间
    String ProductUpdateStartTime;//选填	请求更新开始时间
    String ProductUpdateEndTime;//选填	请求更新结束时间
    Integer NextToken;//第二次回传时填写(用于分页)	NextToken
    String ClientSKUs;//自定义SKU，多选用英文逗号隔开

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

    @XmlElement(name="SKU")
    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    @XmlElement(name="ProductGroupSKU")
    public String getProductGroupSKU() {
        return ProductGroupSKU;
    }

    public void setProductGroupSKU(String productGroupSKU) {
        ProductGroupSKU = productGroupSKU;
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

    @XmlElement(name="ProductUpdateStartTime")
    public String getProductUpdateStartTime() {
        return ProductUpdateStartTime;
    }

    public void setProductUpdateStartTime(String productUpdateStartTime) {
        ProductUpdateStartTime = productUpdateStartTime;
    }

    @XmlElement(name="ProductUpdateEndTime")
    public String getProductUpdateEndTime() {
        return ProductUpdateEndTime;
    }

    public void setProductUpdateEndTime(String productUpdateEndTime) {
        ProductUpdateEndTime = productUpdateEndTime;
    }

    @XmlElement(name="NextToken")
    public Integer getNextToken() {
        return NextToken;
    }

    public void setNextToken(Integer nextToken) {
        NextToken = nextToken;
    }

    @XmlElement(name="ClientSKUs")
    public String getClientSKUs() {
        return ClientSKUs;
    }

    public void setClientSKUs(String clientSKUs) {
        ClientSKUs = clientSKUs;
    }
}
