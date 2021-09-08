package com.upedge.thirdparty.saihe.entity.getProducts;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/25.
 */
public class ApiProductImage {

    String AdminName;//上传者姓名
    String SKU;//产品SKU
    String Width;//图片宽度
    String Height;//图片高度
    String ImageUrl;//图片链接地址
    Boolean IsCover;//是否封面图
    String ImageMemo;//图片备注

    @XmlElement(name="AdminName")
    public String getAdminName() {
        return AdminName;
    }

    public void setAdminName(String adminName) {
        AdminName = adminName;
    }

    @XmlElement(name="SKU")
    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    @XmlElement(name="Width")
    public String getWidth() {
        return Width;
    }

    public void setWidth(String width) {
        Width = width;
    }

    @XmlElement(name="Height")
    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    @XmlElement(name="ImageUrl")
    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    @XmlElement(name="IsCover")
    public Boolean getCover() {
        return IsCover;
    }

    public void setCover(Boolean cover) {
        IsCover = cover;
    }

    @XmlElement(name="ImageMemo")
    public String getImageMemo() {
        return ImageMemo;
    }

    public void setImageMemo(String imageMemo) {
        ImageMemo = imageMemo;
    }
}
