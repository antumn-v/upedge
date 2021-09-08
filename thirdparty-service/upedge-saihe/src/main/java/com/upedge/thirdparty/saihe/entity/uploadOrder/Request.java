package com.upedge.thirdparty.saihe.entity.uploadOrder;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/11.
 */
public class Request {

    //用户名
    String UserName;

    //密码
    String Password;

    //客户号
    Integer Customer_ID;

    @XmlElement(name="ApiUploadOrderInfo")
    public ApiUploadOrderInfo getApiUploadOrderInfo() {
        return ApiUploadOrderInfo;
    }

    public void setApiUploadOrderInfo(ApiUploadOrderInfo apiUploadOrderInfo) {
        ApiUploadOrderInfo = apiUploadOrderInfo;
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

    @XmlElement(name="CustomerID")
    public Integer getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(Integer customer_ID) {
        Customer_ID = customer_ID;
    }

    //上传订单信息实体
    ApiUploadOrderInfo ApiUploadOrderInfo;



}
