package com.upedge.thirdparty.saihe.entity.GetProductInventory;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

/**
 * Created by guoxing on 2019/9/9.
 */
public class ApiProductInventory {

    //产品SKU
    String SKU;
    //自定义SKU
    String ClientSKU;
    //可用库存数量
    Integer GoodNum;
    //锁定库存数量
    Integer LockNum;
    //仓库名称
    String WarehouseName;
    //库存更新时间
    Date UpdateTime;
    //亚马逊标准识别号
    String ASIN;
    //渠道SKU
    String SellerSKU;
    //活跃天数(库龄)
    Integer ActiveDays;
    //活跃时间
    Date ActiveTime;
    //库位
    String PositionCode;
    //采购中
    Integer ProcessingNum;
    //历史入库
    Integer HistoryInNum;
    //历史出库
    Integer HistoryOutNum;


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

    @XmlElement(name="GoodNum")
    public Integer getGoodNum() {
        return GoodNum;
    }

    public void setGoodNum(Integer goodNum) {
        GoodNum = goodNum;
    }

    @XmlElement(name="LockNum")
    public Integer getLockNum() {
        return LockNum;
    }

    public void setLockNum(Integer lockNum) {
        LockNum = lockNum;
    }

    @XmlElement(name="WarehouseName")
    public String getWarehouseName() {
        return WarehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        WarehouseName = warehouseName;
    }

    @XmlElement(name="UpdateTime")
    public Date getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(Date updateTime) {
        UpdateTime = updateTime;
    }

    @XmlElement(name="ASIN")
    public String getASIN() {
        return ASIN;
    }

    public void setASIN(String ASIN) {
        this.ASIN = ASIN;
    }

    @XmlElement(name="SellerSKU")
    public String getSellerSKU() {
        return SellerSKU;
    }

    public void setSellerSKU(String sellerSKU) {
        SellerSKU = sellerSKU;
    }

    @XmlElement(name="ActiveDays")
    public Integer getActiveDays() {
        return ActiveDays;
    }

    public void setActiveDays(Integer activeDays) {
        ActiveDays = activeDays;
    }

    @XmlElement(name="ActiveTime")
    public Date getActiveTime() {
        return ActiveTime;
    }

    public void setActiveTime(Date activeTime) {
        ActiveTime = activeTime;
    }

    @XmlElement(name="PositionCode")
    public String getPositionCode() {
        return PositionCode;
    }

    public void setPositionCode(String positionCode) {
        PositionCode = positionCode;
    }

    @XmlElement(name="ProcessingNum")
    public Integer getProcessingNum() {
        return ProcessingNum;
    }

    public void setProcessingNum(Integer processingNum) {
        ProcessingNum = processingNum;
    }

    @XmlElement(name="HistoryInNum")
    public Integer getHistoryInNum() {
        return HistoryInNum;
    }

    public void setHistoryInNum(Integer historyInNum) {
        HistoryInNum = historyInNum;
    }

    @XmlElement(name="HistoryOutNum")
    public Integer getHistoryOutNum() {
        return HistoryOutNum;
    }

    public void setHistoryOutNum(Integer historyOutNum) {
        HistoryOutNum = historyOutNum;
    }
}
