package com.upedge.thirdparty.ali1688.entity.product;


public class Attribute {

    private Long variantId;

    private String attributeValue;

    private String skuImageUrl;

    private String attributeDisplayName;

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }


    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getSkuImageUrl() {
        return skuImageUrl;
    }

    public void setSkuImageUrl(String skuImageUrl) {
        this.skuImageUrl = skuImageUrl;
    }

    public String getAttributeDisplayName() {
        return attributeDisplayName;
    }

    public void setAttributeDisplayName(String attributeDisplayName) {
        this.attributeDisplayName = attributeDisplayName;
    }
}
