package com.upedge.thirdparty.saihe.entity.getProcurementList;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

/**
 * Created by guoxing on 2020/7/21.
 */
public class ApiProcurementInfo {

    String P_Code;

    Date AddTime;

    Integer WareHouseID;

    String WareHouseName;

    Integer SupplierID;

    String SupplierName;

    String BuyerName;

    String ProcurementListState;

    ProcurementProductList procurementProductList=new ProcurementProductList();

    @XmlElement(name="P_Code")
    public String getP_Code() {
        return P_Code;
    }

    public void setP_Code(String p_Code) {
        P_Code = p_Code;
    }

    @XmlElement(name="AddTime")
    public Date getAddTime() {
        return AddTime;
    }

    public void setAddTime(Date addTime) {
        AddTime = addTime;
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

    @XmlElement(name="SupplierID")
    public Integer getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(Integer supplierID) {
        SupplierID = supplierID;
    }

    @XmlElement(name="SupplierName")
    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    @XmlElement(name="BuyerName")
    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }

    @XmlElement(name="ProcurementListState")
    public String getProcurementListState() {
        return ProcurementListState;
    }

    public void setProcurementListState(String procurementListState) {
        ProcurementListState = procurementListState;
    }

    @XmlElement(name="ProcurementProductList")
    public ProcurementProductList getProcurementProductList() {
        return procurementProductList;
    }

    public void setProcurementProductList(ProcurementProductList procurementProductList) {
        this.procurementProductList = procurementProductList;
    }
}
