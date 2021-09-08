package com.upedge.thirdparty.saihe.entity.processUpdateProduct;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/23.
 */
public class ApiImportProductSupplier {

    String SupplierName;//供应商名称
    Integer SupplierType;//采购类别(1. 市场采购,2. 网络采购,3. 工厂采购)

    @XmlElement(name="SupplierName")
    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    @XmlElement(name="SupplierType")
    public Integer getSupplierType() {
        return SupplierType;
    }

    public void setSupplierType(Integer supplierType) {
        SupplierType = supplierType;
    }
}
