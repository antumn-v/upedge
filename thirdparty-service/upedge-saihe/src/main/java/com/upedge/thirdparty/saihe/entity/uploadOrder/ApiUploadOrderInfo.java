package com.upedge.thirdparty.saihe.entity.uploadOrder;

import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by cjq on 2019/1/11.
 */
public class ApiUploadOrderInfo {

    //客户编号
    Integer CustomerID;

    //来源渠道ID(需在ERP系统中创建)
    Integer OrderSourceID;

    //支付时间
    Date PayTime;

    //客户订单号
    String ClientOrderCode;

    //邮箱
    String Email;

    //交易号
    String TransactionId;

    //币种
    String Currency;

    //订单总金额
    BigDecimal TotalPrice;

    //订单优惠金额
    BigDecimal PromotionDiscountAmount;

    //运费收入
    BigDecimal TransportPay;

    //买家留言
    String OrderDescription;

    //买家姓氏
    String FirstName;

    //买家名字
    String LastName;

    //收货国家
    String Country;

    @XmlElement(name="OrderItemList")
    public OrderItemList getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(OrderItemList orderItemList) {
        this.orderItemList = orderItemList;
    }

    public void setOrderItemList(List<ApiUploadOrderList> orderItemList) {
        this.orderItemList.setOrderItemList(orderItemList);
    }

    //收货省份
    String Province;

    //收货城市
    String City;

    //邮编
    String PostCode;

    //电话
    String Telephone;

    //收货地址一
    String Address1;

    //收货地址二
    String Address2;

    //追踪号
    String TrackNumbers;

    OrderItemList orderItemList=new OrderItemList();

    //发货仓库ID
    Integer WareHouseID;

    //运输方式ID
    Integer TransportID;

    //是否执行订单策略(默认为:true；如果为false，则必须传入WareHouseID和TransportID参数)
    Boolean IsOperateMatch;

    //客户订单对应平台账号, 判断该平台的卖家唯一ID
    String ClientUserAccount;

    @XmlElement(name="ClientUserAccount")
    public String getClientUserAccount() {
        return ClientUserAccount;
    }

    public void setClientUserAccount(String clientUserAccount) {
        ClientUserAccount = clientUserAccount;
    }

    @XmlElement(name="CustomerID")
    public Integer getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(Integer customerID) {
        CustomerID = customerID;
    }

    @XmlElement(name="OrderSourceID")
    public Integer getOrderSourceID() {
        return OrderSourceID;
    }

    public void setOrderSourceID(Integer orderSourceID) {
        OrderSourceID = orderSourceID;
    }

    @XmlElement(name="PayTime")
    public Date getPayTime() {
        return PayTime;
    }

    public void setPayTime(Date payTime) {
        PayTime = payTime;
    }

    @XmlElement(name="ClientOrderCode")
    public String getClientOrderCode() {
        return ClientOrderCode;
    }

    public void setClientOrderCode(String clientOrderCode) {
        ClientOrderCode = clientOrderCode;
    }

    @XmlElement(name="Email")
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @XmlElement(name="TransactionId")
    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    @XmlElement(name="Currency")
    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    @XmlElement(name="TotalPrice")
    public BigDecimal getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        TotalPrice = totalPrice;
    }

    @XmlElement(name="PromotionDiscountAmount")
    public BigDecimal getPromotionDiscountAmount() {
        return PromotionDiscountAmount;
    }

    public void setPromotionDiscountAmount(BigDecimal promotionDiscountAmount) {
        PromotionDiscountAmount = promotionDiscountAmount;
    }

    @XmlElement(name="TransportPay")
    public BigDecimal getTransportPay() {
        return TransportPay;
    }

    public void setTransportPay(BigDecimal transportPay) {
        TransportPay = transportPay;
    }

    @XmlElement(name="OrderDescription")
    public String getOrderDescription() {
        return OrderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        OrderDescription = orderDescription;
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

    @XmlElement(name="Country")
    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    @XmlElement(name="Province")
    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    @XmlElement(name="City")
    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    @XmlElement(name="PostCode")
    public String getPostCode() {
        return PostCode;
    }

    public void setPostCode(String postCode) {
        PostCode = postCode;
    }

    @XmlElement(name="Telephone")
    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    @XmlElement(name="Address1")
    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    @XmlElement(name="Address2")
    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }


    @XmlElement(name="TrackNumbers")
    public String getTrackNumbers() {
        return TrackNumbers;
    }

    public void setTrackNumbers(String trackNumbers) {
        TrackNumbers = trackNumbers;
    }

    @XmlElement(name="WareHouseID")
    public Integer getWareHouseID() {
        return WareHouseID;
    }

    public void setWareHouseID(Integer wareHouseID) {
        WareHouseID = wareHouseID;
    }

    @XmlElement(name="TransportID")
    public Integer getTransportID() {
        return TransportID;
    }

    public void setTransportID(Integer transportID) {
        TransportID = transportID;
    }

    @XmlElement(name="IsOperateMatch")
    public Boolean getIsOperateMatch() {
        return IsOperateMatch;
    }

    public void setIsOperateMatch(Boolean isOperateMatch) {
        this.IsOperateMatch = isOperateMatch;
    }
}
