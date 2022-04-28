package com.upedge.pms.modules.alibaba.entity.product;

/**
 * Created by guoxing on 2020/6/15.
 */
public class ProductAttribute {

    /**
     * 属性ID
     */
    Long attributeID;

    /**
     * 属性名称
     */
    String attributeName;

    /**
     * 属性值ID
     */
    Long valueID;

    /**
     * 属性值
     */
    String value;

    /**
     * 是否为自定义属性，国际站无需关注
     */
    boolean isCustom;

    public Long getAttributeID() {
        return attributeID;
    }

    public void setAttributeID(Long attributeID) {
        this.attributeID = attributeID;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Long getValueID() {
        return valueID;
    }

    public void setValueID(Long valueID) {
        this.valueID = valueID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }
}
