package com.upedge.thirdparty.ali1688.entity.product;

import java.util.List;

/**
 * Created by jiaqi on 2020/6/15.
 */
public class ProductImageInfo {

    /**
     * 主图列表，使用相对路径，需要增加域名：https://cbu01.alicdn.com/
     */
    List<String> images;

    /**
     * 是否打水印，是(true)或否(false)，1688无需关注此字段，
     * 1688的水印信息在上传图片时处理
     */
    Boolean isWatermark;

    /**
     * 水印是否有边框，有边框(true)或者无边框(false)，
     * 1688无需关注此字段，1688的水印信息在上传图片时处理
     */
    Boolean isWatermarkFrame;

    /**
     * 水印位置，在中间(center)或者在底部(bottom)，1688无需关注此字段，
     * 1688的水印信息在上传图片时处理
     */
    String watermarkPosition;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Boolean getWatermark() {
        return isWatermark;
    }

    public void setWatermark(Boolean watermark) {
        isWatermark = watermark;
    }

    public Boolean getWatermarkFrame() {
        return isWatermarkFrame;
    }

    public void setWatermarkFrame(Boolean watermarkFrame) {
        isWatermarkFrame = watermarkFrame;
    }

    public String getWatermarkPosition() {
        return watermarkPosition;
    }

    public void setWatermarkPosition(String watermarkPosition) {
        this.watermarkPosition = watermarkPosition;
    }
}
