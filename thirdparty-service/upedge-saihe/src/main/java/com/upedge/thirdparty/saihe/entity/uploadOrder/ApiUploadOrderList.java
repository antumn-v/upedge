package com.upedge.thirdparty.saihe.entity.uploadOrder;

import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

/**
 * Created by cjq on 2019/1/11.
 */
public class ApiUploadOrderList {


    //卖家SKU
    String SellerSKU;

    //订单产品数量
    Integer ProductNum;

    //订单产品销售单价
    BigDecimal ProductPrice;

    //订单产品运费单价
    BigDecimal ShippingPrice;

    String OrderItemId;

    @XmlElement(name="SellerSKU")
    public String getSellerSKU() {
        return SellerSKU;
    }

    public void setSellerSKU(String sellerSKU) {
        SellerSKU = sellerSKU;
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

    @XmlElement(name="ShippingPrice")
    public BigDecimal getShippingPrice() {
        return ShippingPrice;
    }

    public void setShippingPrice(BigDecimal shippingPrice) {
        ShippingPrice = shippingPrice;
    }

    @XmlElement(name="OrderItemId")
    public String getOrderItemId() {
        return OrderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        OrderItemId = orderItemId;
    }
}
