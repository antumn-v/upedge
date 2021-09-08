package com.upedge.thirdparty.saihe.entity.getPackages;

import com.upedge.thirdparty.saihe.entity.getTransportList.ApiTransport;

import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cjq on 2019/5/15.
 */
public class ApiPackageInfo {
     //包裹单号
     Integer PackageID;
     //包裹目的国家
     String PackageCountry;
    //包裹发货时间
     Date ShipTime;
    //包裹运输方式
     ApiTransport Transport;
    //包裹重量(g)
     Integer PackageWeight;
    //包裹订单列表
    OrderInfoList OrderInfoList=new OrderInfoList();
    //包裹运费
     BigDecimal FinalShippingFee;
    //追踪号
     String TrackNumber;
    //入库人
     String PutinUserName;
    //订单总金额(美元)
     BigDecimal OrderAmount;
    //采购总金额（RMB）
     BigDecimal PurchaseAmount;
    //自定义sku，多个用逗号隔开
     String ClientSKU;
    //产品总件数
     Integer Quantity;
    //称重人
     String OperationUserName;
    //运费 折前价
     BigDecimal Price;
    //运费 总折扣
     BigDecimal Discount;
    //运费 折后价
     BigDecimal DiscountPrice;
    //运费 挂号费
     BigDecimal RegistrationFee;
    //物流商单号
     String logisticsID;
    //包裹生成时间
     Date PackageAddTime;




    @XmlElement(name="PackageID")
    public Integer getPackageID() {
        return PackageID;
    }

    public void setPackageID(Integer packageID) {
        PackageID = packageID;
    }

    @XmlElement(name="PackageCountry")
    public String getPackageCountry() {
        return PackageCountry;
    }

    public void setPackageCountry(String packageCountry) {
        PackageCountry = packageCountry;
    }

    @XmlElement(name="ShipTime")
    public Date getShipTime() {
        return ShipTime;
    }

    public void setShipTime(Date shipTime) {
        ShipTime = shipTime;
    }

    @XmlElement(name="Transport")
    public ApiTransport getTransport() {
        return Transport;
    }

    public void setTransport(ApiTransport transport) {
        Transport = transport;
    }

    @XmlElement(name="PackageWeight")
    public Integer getPackageWeight() {
        return PackageWeight;
    }

    public void setPackageWeight(Integer packageWeight) {
        PackageWeight = packageWeight;
    }

    @XmlElement(name="OrderInfoList")
    public OrderInfoList getOrderInfoList() {
        return OrderInfoList;
    }

    public void setOrderInfoList(OrderInfoList orderInfoList) {
        OrderInfoList = orderInfoList;
    }



    @XmlElement(name="FinalShippingFee")
    public BigDecimal getFinalShippingFee() {
        return FinalShippingFee;
    }

    public void setFinalShippingFee(BigDecimal finalShippingFee) {
        FinalShippingFee = finalShippingFee;
    }

    @XmlElement(name="TrackNumber")
    public String getTrackNumber() {
        return TrackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        TrackNumber = trackNumber;
    }

    @XmlElement(name="PutinUserName")
    public String getPutinUserName() {
        return PutinUserName;
    }

    public void setPutinUserName(String putinUserName) {
        PutinUserName = putinUserName;
    }

    @XmlElement(name="OrderAmount")
    public BigDecimal getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        OrderAmount = orderAmount;
    }

    @XmlElement(name="PurchaseAmount")
    public BigDecimal getPurchaseAmount() {
        return PurchaseAmount;
    }

    public void setPurchaseAmount(BigDecimal purchaseAmount) {
        PurchaseAmount = purchaseAmount;
    }

    @XmlElement(name="ClientSKU")
    public String getClientSKU() {
        return ClientSKU;
    }

    public void setClientSKU(String clientSKU) {
        ClientSKU = clientSKU;
    }

    @XmlElement(name="Quantity")
    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    @XmlElement(name="OperationUserName")
    public String getOperationUserName() {
        return OperationUserName;
    }

    public void setOperationUserName(String operationUserName) {
        OperationUserName = operationUserName;
    }

    @XmlElement(name="Price")
    public BigDecimal getPrice() {
        return Price;
    }

    public void setPrice(BigDecimal price) {
        Price = price;
    }

    @XmlElement(name="Discount")
    public BigDecimal getDiscount() {
        return Discount;
    }

    public void setDiscount(BigDecimal discount) {
        Discount = discount;
    }

    @XmlElement(name="DiscountPrice")
    public BigDecimal getDiscountPrice() {
        return DiscountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        DiscountPrice = discountPrice;
    }

    @XmlElement(name="RegistrationFee")
    public BigDecimal getRegistrationFee() {
        return RegistrationFee;
    }

    public void setRegistrationFee(BigDecimal registrationFee) {
        RegistrationFee = registrationFee;
    }

    @XmlElement(name="logisticsID")
    public String getLogisticsID() {
        return logisticsID;
    }

    public void setLogisticsID(String logisticsID) {
        this.logisticsID = logisticsID;
    }

    @XmlElement(name="PackageAddTime")
    public Date getPackageAddTime() {
        return PackageAddTime;
    }

    public void setPackageAddTime(Date packageAddTime) {
        PackageAddTime = packageAddTime;
    }
}
