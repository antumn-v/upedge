package com.upedge.thirdparty.saihe.entity.getOrderByCode;

import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cjq on 2019/1/14.
 */
public class ApiOrderInfo {
     //订单号
     String OrderCode;
     //平台订单号
     String ClientOrderCode;
     //Ebay订单号
     Integer SalesRecordNumber;
     //交易号
     String TransactionId;
     //客户ID
     String ClientUserAccount;
     //邮箱
     String Email;
    //电话
     String Telephone;
     //来源渠道ID
     Integer OrderSourceID;
     //来源渠道名称
     String OrderSourceName;
     //付款状态
     Boolean IsPay;
     //订单状态(正常 = 0,待审查 = 1,作废 = 2,锁定 = 3,只锁定发货 = 4,已完成 = 5)
     Integer OrderStatus;
     //订单发货状态(未发货 = 0,部分发货 = 1,全部发货 = 2,妥投 = 3)
     Integer OrderState;
     //订单添加时间
     String AddTime;
     //订单支付时间
     String PayTime;
     //订单最后修改时间
     String UpdateTime;
     //订单使用币种
     String Currency;
     //订单金额
     BigDecimal TotalPrice;
     //客户支付运费
     BigDecimal TransportPay;
     //收货人国家
     String Country;
     //收货人省份
     String Province;
     //收货人城市
     String City;
     //收货人公司名称
     String CompanyName;
     //邮编
     String PostCode;
     //收货人FirstName
     String FirstName;
     //收货人LastName
     String LastName;
    //	总地址（地址1+地址2+地址3）
     String Address;
     //地址1
     String Address1;
     //地址2
     String Address2;
     //地址3
     String Address3;
     //发货方式ID
     Integer TransportID;
    //发货方式
     String TransportName;
    //订单备注
     String OrderDescription;
    //是否是FBA订单
     Boolean IsFBAOrder	;
     //发货仓库ID
     Integer WareHouseID;
     //	促销折扣金额
     BigDecimal PromotionDiscountAmount;
     //	平台支付方式
     String PaymentMethods;
     //	订单总重量
     BigDecimal ProductWeight;
     //订单产品实体集合
     List<ApiOrderList> OrderList;
     //	平台配送服务
     String ShipService;
    //订单追踪号
     String TrackNumbers;
     //	Paypal手续费
     BigDecimal PaypalFee;
     //	退款返还Paypal手续费
     BigDecimal RefundPaypalFee;
     //物流商跟踪号
     String LogisticsOrderNo;
     //	是否备库订单
     Boolean IsStockOrder;
     //	亚马逊备库单ID
     String ShipmentID;

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

    @XmlElement(name="SalesRecordNumber")
    public Integer getSalesRecordNumber() {
        return SalesRecordNumber;
    }

    public void setSalesRecordNumber(Integer salesRecordNumber) {
        SalesRecordNumber = salesRecordNumber;
    }

    @XmlElement(name="TransactionId")
    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    @XmlElement(name="ClientUserAccount")
    public String getClientUserAccount() {
        return ClientUserAccount;
    }

    public void setClientUserAccount(String clientUserAccount) {
        ClientUserAccount = clientUserAccount;
    }

    @XmlElement(name="Email")
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @XmlElement(name="Telephone")
    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    @XmlElement(name="OrderSourceID")
    public Integer getOrderSourceID() {
        return OrderSourceID;
    }

    public void setOrderSourceID(Integer orderSourceID) {
        OrderSourceID = orderSourceID;
    }

    @XmlElement(name="OrderSourceName")
    public String getOrderSourceName() {
        return OrderSourceName;
    }

    public void setOrderSourceName(String orderSourceName) {
        OrderSourceName = orderSourceName;
    }

    @XmlElement(name="IsPay")
    public Boolean getPay() {
        return IsPay;
    }

    public void setPay(Boolean pay) {
        IsPay = pay;
    }


    @XmlElement(name="OrderStatus")
    public Integer getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        OrderStatus = orderStatus;
    }

    @XmlElement(name="OrderState")
    public Integer getOrderState() {
        return OrderState;
    }

    public void setOrderState(Integer orderState) {
        OrderState = orderState;
    }

    @XmlElement(name="AddTime")
    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String addTime) {
        AddTime = addTime;
    }

    @XmlElement(name="PayTime")
    public String getPayTime() {
        return PayTime;
    }

    public void setPayTime(String payTime) {
        PayTime = payTime;
    }

    @XmlElement(name="UpdateTime")
    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
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

    @XmlElement(name="TransportPay")
    public BigDecimal getTransportPay() {
        return TransportPay;
    }

    public void setTransportPay(BigDecimal transportPay) {
        TransportPay = transportPay;
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

    @XmlElement(name="CompanyName")
    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    @XmlElement(name="PostCode")
    public String getPostCode() {
        return PostCode;
    }

    public void setPostCode(String postCode) {
        PostCode = postCode;
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

    @XmlElement(name="Address")
    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
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

    @XmlElement(name="Address3")
    public String getAddress3() {
        return Address3;
    }

    public void setAddress3(String address3) {
        Address3 = address3;
    }

    @XmlElement(name="TransportID")
    public Integer getTransportID() {
        return TransportID;
    }

    public void setTransportID(Integer transportID) {
        TransportID = transportID;
    }

    @XmlElement(name="TransportName")
    public String getTransportName() {
        return TransportName;
    }

    public void setTransportName(String transportName) {
        TransportName = transportName;
    }

    @XmlElement(name="OrderDescription")
    public String getOrderDescription() {
        return OrderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        OrderDescription = orderDescription;
    }

    public Boolean getFBAOrder() {
        return IsFBAOrder;
    }

    public void setFBAOrder(Boolean FBAOrder) {
        IsFBAOrder = FBAOrder;
    }

    @XmlElement(name="WareHouseID")
    public Integer getWareHouseID() {
        return WareHouseID;
    }

    public void setWareHouseID(Integer wareHouseID) {
        WareHouseID = wareHouseID;
    }

    public BigDecimal getPromotionDiscountAmount() {
        return PromotionDiscountAmount;
    }

    public void setPromotionDiscountAmount(BigDecimal promotionDiscountAmount) {
        PromotionDiscountAmount = promotionDiscountAmount;
    }

    public String getPaymentMethods() {
        return PaymentMethods;
    }

    public void setPaymentMethods(String paymentMethods) {
        PaymentMethods = paymentMethods;
    }

    public BigDecimal getProductWeight() {
        return ProductWeight;
    }

    public void setProductWeight(BigDecimal productWeight) {
        ProductWeight = productWeight;
    }

    public List<ApiOrderList> getOrderList() {
        return OrderList;
    }

    public void setOrderList(List<ApiOrderList> orderList) {
        OrderList = orderList;
    }

    @XmlElement(name="ShipService")
    public String getShipService() {
        return ShipService;
    }

    public void setShipService(String shipService) {
        ShipService = shipService;
    }


    @XmlElement(name="TrackNumbers")
    public String getTrackNumbers() {
        return TrackNumbers;
    }

    public void setTrackNumbers(String trackNumbers) {
        TrackNumbers = trackNumbers;
    }

    public BigDecimal getPaypalFee() {
        return PaypalFee;
    }

    public void setPaypalFee(BigDecimal paypalFee) {
        PaypalFee = paypalFee;
    }

    public BigDecimal getRefundPaypalFee() {
        return RefundPaypalFee;
    }

    public void setRefundPaypalFee(BigDecimal refundPaypalFee) {
        RefundPaypalFee = refundPaypalFee;
    }

    @XmlElement(name="LogisticsOrderNo")
    public String getLogisticsOrderNo() {
        return LogisticsOrderNo;
    }

    public void setLogisticsOrderNo(String logisticsOrderNo) {
        LogisticsOrderNo = logisticsOrderNo;
    }

    public Boolean getStockOrder() {
        return IsStockOrder;
    }

    public void setStockOrder(Boolean stockOrder) {
        IsStockOrder = stockOrder;
    }

    public String getShipmentID() {
        return ShipmentID;
    }

    public void setShipmentID(String shipmentID) {
        ShipmentID = shipmentID;
    }
}
