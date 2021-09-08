package com.upedge.thirdparty.saihe.entity.processUpdateProduct;

import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

/**
 * Created by cjq on 2019/1/23.
 */
public class ApiImportProductSupplierPrice {

    String SupplierSKU;//供应商产品编码
    BigDecimal ProcessPrice;//处理费
    BigDecimal OtherPrice;//	其他费
    Integer ProcurementDay;//	采购天数
    String WebProductUrl;//网络采购链接

    @XmlElement(name="WebProductUrl")
    public String getWebProductUrl() {
        return WebProductUrl;
    }

    public void setWebProductUrl(String webProductUrl) {
        WebProductUrl = webProductUrl;
    }

    @XmlElement(name="SupplierSKU")
    public String getSupplierSKU() {
        return SupplierSKU;
    }

    public void setSupplierSKU(String supplierSKU) {
        SupplierSKU = supplierSKU;
    }

    @XmlElement(name="ProcessPrice")
    public BigDecimal getProcessPrice() {
        return ProcessPrice;
    }

    public void setProcessPrice(BigDecimal processPrice) {
        ProcessPrice = processPrice;
    }

    @XmlElement(name="OtherPrice")
    public BigDecimal getOtherPrice() {
        return OtherPrice;
    }

    public void setOtherPrice(BigDecimal otherPrice) {
        OtherPrice = otherPrice;
    }

    @XmlElement(name="ProcurementDay")
    public Integer getProcurementDay() {
        return ProcurementDay;
    }

    public void setProcurementDay(Integer procurementDay) {
        ProcurementDay = procurementDay;
    }
}
