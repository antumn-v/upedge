package com.upedge.thirdparty.ali1688.entity.product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiaqi on 2020/6/15.
 */
public class ProductSKUInfo {

    /**
     * SKU属性值，可填多组信息
     */
    List<SKUAttrInfo> attributes;

    /**
     * 指定规格的货号，国际站无需关注
     */
    String cargoNumber;

    /**
     * 可销售数量，国际站无需关注
     */
    Integer amountOnSale;

    /**
     * 建议零售价，国际站无需关注
     */
    Double retailPrice;

    /**
     * 报价时该规格的单价，国际站注意要点：
     * 含有SKU属性的在线批发产品设定具体价格时使用此值，
     * 若设置阶梯价格则使用priceRange
     */
    Double price;

    String skuId;

    String specId;

    public List<Attribute> getAttributes() {
        List<Attribute> attributeList=new ArrayList<>();
        if(attributes==null){
           return attributeList;
        }
        for(SKUAttrInfo skuAttrInfo:attributes){
            Attribute attribute=new Attribute();
            attribute.setAttributeValue(skuAttrInfo.getAttributeValue());
            attribute.setSkuImageUrl(skuAttrInfo.getSkuImageUrl());
            attribute.setAttributeDisplayName(skuAttrInfo.getAttributeDisplayName());
            attributeList.add(attribute);
        }
        return attributeList;
    }

    public void setAttributes(List<SKUAttrInfo> attributes) {
        this.attributes = attributes;
    }

    public String getCargoNumber() {
        return cargoNumber;
    }

    public void setCargoNumber(String cargoNumber) {
        this.cargoNumber = cargoNumber;
    }

    public Integer getAmountOnSale() {
        return amountOnSale;
    }

    public void setAmountOnSale(Integer amountOnSale) {
        this.amountOnSale = amountOnSale;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }
}
