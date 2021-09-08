package com.upedge.thirdparty.saihe.entity.createProcurement;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/8/26.
 */
public class ApiProcurementDetailList {

    String P_Code;//采购单号

    Integer DetailID;//采购详情ID

    String SKU;

    Integer ProductNum;//数量

    @XmlElement(name="P_Code")
    public String getP_Code() {
        return P_Code;
    }

    public void setP_Code(String p_Code) {
        P_Code = p_Code;
    }

    @XmlElement(name="DetailID")
    public Integer getDetailID() {
        return DetailID;
    }

    public void setDetailID(Integer detailID) {
        DetailID = detailID;
    }

    @XmlElement(name="SKU")
    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    @XmlElement(name="ProductNum")
    public Integer getProductNum() {
        return ProductNum;
    }

    public void setProductNum(Integer productNum) {
        ProductNum = productNum;
    }
}
