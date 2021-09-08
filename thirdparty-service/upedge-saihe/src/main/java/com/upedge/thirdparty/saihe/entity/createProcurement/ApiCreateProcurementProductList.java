package com.upedge.thirdparty.saihe.entity.createProcurement;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/8/26.
 */
public class ApiCreateProcurementProductList {

    String SKU;//SKU

    Integer Num;//数量

    @XmlElement(name="SKU")
    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    @XmlElement(name="Num")
    public Integer getNum() {
        return Num;
    }

    public void setNum(Integer num) {
        Num = num;
    }
}
