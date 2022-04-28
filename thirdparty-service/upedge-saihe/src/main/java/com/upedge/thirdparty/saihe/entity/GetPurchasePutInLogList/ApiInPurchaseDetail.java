package com.upedge.thirdparty.saihe.entity.GetPurchasePutInLogList;

import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by guoxing on 2020/2/21.
 */
public class ApiInPurchaseDetail {

    //入库ID
    private Integer ID;
    //采购单号
    private String P_Code;
    //下单号
    private String Single_NO;
    //供应商ID
    private String SupplierID;
    //供应商名称
    private String SupplierName;
    //仓库ID
    private Integer WareHouseID;
    //仓库名称
    private String WareHouseName;
    //SKU
    private String SKU;
    //自定义SKU
    private String ClientSKU;
    //采购价格
    private BigDecimal BuyPrice;
    //采购运费
    private BigDecimal BuyShipPrice;
    //采购单需求数量
    private Integer ProductNum;
    //入库数量
    private Integer PutInNum;
    //入库时间
    private Date PutTime;
    //入库人ID
    private Integer AdminID;
    //入库人
    private String AdminName;

    @XmlElement(name="ID")
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    @XmlElement(name="P_Code")
    public String getP_Code() {
        return P_Code;
    }

    public void setP_Code(String p_Code) {
        P_Code = p_Code;
    }

    @XmlElement(name="Single_NO")
    public String getSingle_NO() {
        return Single_NO;
    }

    public void setSingle_NO(String single_NO) {
        Single_NO = single_NO;
    }

    @XmlElement(name="SupplierID")
    public String getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(String supplierID) {
        SupplierID = supplierID;
    }

    @XmlElement(name="SupplierName")
    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    @XmlElement(name="WareHouseID")
    public Integer getWareHouseID() {
        return WareHouseID;
    }

    public void setWareHouseID(Integer wareHouseID) {
        WareHouseID = wareHouseID;
    }

    @XmlElement(name="WareHouseName")
    public String getWareHouseName() {
        return WareHouseName;
    }

    public void setWareHouseName(String wareHouseName) {
        WareHouseName = wareHouseName;
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

    @XmlElement(name="BuyPrice")
    public BigDecimal getBuyPrice() {
        return BuyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        BuyPrice = buyPrice;
    }

    @XmlElement(name="BuyShipPrice")
    public BigDecimal getBuyShipPrice() {
        return BuyShipPrice;
    }

    public void setBuyShipPrice(BigDecimal buyShipPrice) {
        BuyShipPrice = buyShipPrice;
    }

    @XmlElement(name="ProductNum")
    public Integer getProductNum() {
        return ProductNum;
    }

    public void setProductNum(Integer productNum) {
        ProductNum = productNum;
    }

    @XmlElement(name="PutInNum")
    public Integer getPutInNum() {
        return PutInNum;
    }

    public void setPutInNum(Integer putInNum) {
        PutInNum = putInNum;
    }

    @XmlElement(name="PutTime")
    public Date getPutTime() {
        return PutTime;
    }

    public void setPutTime(Date putTime) {
        PutTime = putTime;
    }

    @XmlElement(name="AdminID")
    public Integer getAdminID() {
        return AdminID;
    }

    public void setAdminID(Integer adminID) {
        AdminID = adminID;
    }

    @XmlElement(name="AdminName")
    public String getAdminName() {
        return AdminName;
    }

    public void setAdminName(String adminName) {
        AdminName = adminName;
    }
}
