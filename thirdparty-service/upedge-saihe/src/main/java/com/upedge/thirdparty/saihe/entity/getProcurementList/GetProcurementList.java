package com.upedge.thirdparty.saihe.entity.getProcurementList;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

/**
 * Created by guoxing on 2020/7/21.
 */
public class GetProcurementList {

    Integer CustomerID;//商户号

    String UserName;//用户名

    String Password;//密码

    String P_Code;

    //时间开始
    Date startTime;
    //时间结束
    Date endTime;

    Integer NextToken;

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

    @XmlElement(name="P_Code")
    public String getP_Code() {
        return P_Code;
    }

    public void setP_Code(String p_Code) {
        P_Code = p_Code;
    }

    @XmlElement(name="StartTime")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @XmlElement(name="EndTime")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @XmlElement(name="NextToken")
    public Integer getNextToken() {
        return NextToken;
    }

    public void setNextToken(Integer nextToken) {
        NextToken = nextToken;
    }
}
