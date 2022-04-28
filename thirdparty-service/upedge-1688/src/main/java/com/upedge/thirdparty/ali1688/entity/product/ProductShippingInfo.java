package com.upedge.thirdparty.ali1688.entity.product;

/**
 * Created by guoxing on 2020/6/15.
 */
public class ProductShippingInfo {

    /**
     * 重量/毛重，单位千克/件
     */
    Double unitWeight;

    /**
     * 体积，单位是立方厘米，范围是1-9999999，1688无需关注此字段
     */
    Integer volume;

    /**
     *备货期，单位是天，范围是1-60。1688无需处理此字段
     */
    Integer handlingTime;

    /**
     *运费模板ID，0表示运费说明，1表示卖家承担运费，
     *其他值表示使用运费模版。此参数可调用运费模板相关API获取
     */
    Long freightTemplateID;

    /**
     * 净重，单位千克/件
     */
    Double suttleWeight;

    /**
     *发货地描述
     */
    String sendGoodsAddressText;

    /**
     * 宽度，单位厘米
     */
    Double width;

    /**
     *高度，单位厘米
     */
    Double height;

    /**
     * 长度，单位厘米
     */
    Double length;

    /**
     *尺寸，单位是厘米，长宽高范围是1-9999999。1688无需关注此字段
     */
    String packageSize;

    /**
     * 发货地址ID，国际站无需处理此字段
     */
    Long sendGoodsAddressId;

    public Double getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(Double unitWeight) {
        this.unitWeight = unitWeight;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getHandlingTime() {
        return handlingTime;
    }

    public void setHandlingTime(Integer handlingTime) {
        this.handlingTime = handlingTime;
    }

    public Long getFreightTemplateID() {
        return freightTemplateID;
    }

    public void setFreightTemplateID(Long freightTemplateID) {
        this.freightTemplateID = freightTemplateID;
    }

    public Double getSuttleWeight() {
        return suttleWeight;
    }

    public void setSuttleWeight(Double suttleWeight) {
        this.suttleWeight = suttleWeight;
    }

    public String getSendGoodsAddressText() {
        return sendGoodsAddressText;
    }

    public void setSendGoodsAddressText(String sendGoodsAddressText) {
        this.sendGoodsAddressText = sendGoodsAddressText;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(String packageSize) {
        this.packageSize = packageSize;
    }

    public Long getSendGoodsAddressId() {
        return sendGoodsAddressId;
    }

    public void setSendGoodsAddressId(Long sendGoodsAddressId) {
        this.sendGoodsAddressId = sendGoodsAddressId;
    }
}
