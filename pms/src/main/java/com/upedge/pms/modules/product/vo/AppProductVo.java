package com.upedge.pms.modules.product.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AppProductVo {

    private Long id;

    /**
     * 商品标题
     */
    private String productTitle;
    /**
     * 商品主图
     */
    private String productImage;

    private String priceRange;

    private Long categoryId;

    /**
     * 1688上30天成交量
     */
    private Integer turnover;

    /**
     * 1688上的评分
     */
    private Integer score;

    private Integer importState;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Date createTime;

    private Date updateTime;


    public void initPriceRange(){
        if (this.minPrice == null){
            this.minPrice = BigDecimal.ZERO;
        }
        if (this.maxPrice == null){
            this.maxPrice = BigDecimal.ZERO;
        }
        int i = this.minPrice.compareTo(this.maxPrice);
        switch (i){
            case 0:
                this.setPriceRange(this.minPrice.toString());
                break;
            case 1:
                this.setPriceRange(this.maxPrice + "~" + this.minPrice);
                break;
            case -1:
                this.setPriceRange(this.minPrice + "~" + this.maxPrice);
                break;
            default:
                this.setPriceRange(this.minPrice.toString());
                break;
        }


    }
}