package com.upedge.thirdparty.ali1688.entity.product;

/**
 * Created by guoxing on 2020/6/15.
 */
public class SKUAttrInfo {

    /**
     *sku属性ID
     */
    Long attributeID;

    /**
     * sku值ID，1688不用关注
     */
    Long attValueID;

    /**
     *sku值内容，国际站不用关注
     */
    String attributeValue;

    /**
     * 自定义属性值名称，1688无需关注
     */
    String customValueName;

    /**
     *sku图片
     */
    String skuImageUrl;

    /**
     *sku属性ID所对应的显示名，比如颜色，尺码
     */
    String attributeDisplayName;


    public Long getAttributeID() {
        return attributeID;
    }

    public void setAttributeID(Long attributeID) {
        this.attributeID = attributeID;
    }

    public Long getAttValueID() {
        return attValueID;
    }

    public void setAttValueID(Long attValueID) {
        this.attValueID = attValueID;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getCustomValueName() {
        return customValueName;
    }

    public void setCustomValueName(String customValueName) {
        this.customValueName = customValueName;
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
