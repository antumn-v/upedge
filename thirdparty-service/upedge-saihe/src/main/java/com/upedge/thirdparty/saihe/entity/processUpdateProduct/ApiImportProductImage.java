package com.upedge.thirdparty.saihe.entity.processUpdateProduct;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/23.
 */
public class ApiImportProductImage {

    Integer IsCover;//是否封面（每个SKU只能由一张封面）
    String OriginalImageUrl;//图片URL
    Integer SortBy;//排序

    @XmlElement(name="IsCover")
    public Integer getCover() {
        return IsCover;
    }

    public void setCover(Integer cover) {
        IsCover = cover;
    }

    @XmlElement(name="OriginalImageUrl")
    public String getOriginalImageUrl() {
        return OriginalImageUrl;
    }

    public void setOriginalImageUrl(String originalImageUrl) {
        OriginalImageUrl = originalImageUrl;
    }

    @XmlElement(name="SortBy")
    public Integer getSortBy() {
        return SortBy;
    }

    public void setSortBy(Integer sortBy) {
        SortBy = sortBy;
    }
}
