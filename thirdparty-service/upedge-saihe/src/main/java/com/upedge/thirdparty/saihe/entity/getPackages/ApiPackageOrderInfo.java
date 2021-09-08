package com.upedge.thirdparty.saihe.entity.getPackages;

import com.upedge.thirdparty.saihe.entity.getOrderSourceList.ApiOrderSource;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

/**
 * Created by cjq on 2019/5/15.
 */
public class ApiPackageOrderInfo {
    //系统订单号
    String OrderCode;
    //客户自定义单号
    String ClientOrderCode;
    //订单支付时间
    Date PayTime;
    //客户姓
    String FirstName;
    //客户名
    String LastName;
    //订单渠道类型
    ApiOrderSource OrderSource;

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

    @XmlElement(name="PayTime")
    public Date getPayTime() {
        return PayTime;
    }

    public void setPayTime(Date payTime) {
        PayTime = payTime;
    }

    @XmlElement(name="FirstName")
    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    @XmlElement(name="LastName")
    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    @XmlElement(name="OrderSource")
    public ApiOrderSource getOrderSource() {
        return OrderSource;
    }

    public void setOrderSource(ApiOrderSource orderSource) {
        OrderSource = orderSource;
    }
}
