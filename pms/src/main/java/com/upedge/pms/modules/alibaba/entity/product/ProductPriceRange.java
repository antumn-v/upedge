package com.upedge.pms.modules.alibaba.entity.product;

/**
 * Created by guoxing on 2020/6/15.
 */
public class ProductPriceRange {

    /**
     *起批量
     */
    Integer startQuantity;

    /**
     * 商品价格
     */
    Double price;

    public Integer getStartQuantity() {
        return startQuantity;
    }

    public void setStartQuantity(Integer startQuantity) {
        this.startQuantity = startQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
