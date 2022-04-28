package com.upedge.thirdparty.saihe.entity.getProcurementList;

import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

/**
 * Created by guoxing on 2020/7/21.
 */
public class ApiProcurementProductList {

    String P_Code;
    String ClientSKU;
    String ProductGroupSKU;
    String ProductColor;
    String ProductSize;
    Integer ProductNum;
    BigDecimal ProductPrice;
    String ProductNameCN;
    BigDecimal GrandTotal;
    String SKU;
    Integer SingleNum;
    Integer ActualBuyNum;
    Integer CheckNum;

    @XmlElement(name="P_Code")
    public String getP_Code() {
        return P_Code;
    }

    public void setP_Code(String p_Code) {
        P_Code = p_Code;
    }

    @XmlElement(name="ClientSKU")
    public String getClientSKU() {
        return ClientSKU;
    }

    public void setClientSKU(String clientSKU) {
        ClientSKU = clientSKU;
    }

    @XmlElement(name="ProductGroupSKU")
    public String getProductGroupSKU() {
        return ProductGroupSKU;
    }

    public void setProductGroupSKU(String productGroupSKU) {
        ProductGroupSKU = productGroupSKU;
    }

    @XmlElement(name="ProductColor")
    public String getProductColor() {
        return ProductColor;
    }

    public void setProductColor(String productColor) {
        ProductColor = productColor;
    }

    @XmlElement(name="ProductSize")
    public String getProductSize() {
        return ProductSize;
    }

    public void setProductSize(String productSize) {
        ProductSize = productSize;
    }

    @XmlElement(name="ProductNum")
    public Integer getProductNum() {
        return ProductNum;
    }

    public void setProductNum(Integer productNum) {
        ProductNum = productNum;
    }

    @XmlElement(name="ProductPrice")
    public BigDecimal getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        ProductPrice = productPrice;
    }

    @XmlElement(name="ProductNameCN")
    public String getProductNameCN() {
        return ProductNameCN;
    }

    public void setProductNameCN(String productNameCN) {
        ProductNameCN = productNameCN;
    }

    @XmlElement(name="GrandTotal")
    public BigDecimal getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        GrandTotal = grandTotal;
    }

    @XmlElement(name="SKU")
    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    @XmlElement(name="SingleNum")
    public Integer getSingleNum() {
        return SingleNum;
    }

    public void setSingleNum(Integer singleNum) {
        SingleNum = singleNum;
    }

    @XmlElement(name="ActualBuyNum")
    public Integer getActualBuyNum() {
        return ActualBuyNum;
    }

    public void setActualBuyNum(Integer actualBuyNum) {
        ActualBuyNum = actualBuyNum;
    }

    @XmlElement(name="CheckNum")
    public Integer getCheckNum() {
        return CheckNum;
    }

    public void setCheckNum(Integer checkNum) {
        CheckNum = checkNum;
    }
}
