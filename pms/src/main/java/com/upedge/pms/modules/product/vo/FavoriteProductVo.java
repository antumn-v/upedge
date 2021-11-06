package com.upedge.pms.modules.product.vo;

import lombok.Data;

import java.util.Date;

@Data
public class FavoriteProductVo {
    /**
     *
     */
    private Long id;
    /**
     * 商品sku
     */
    private String productSku;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 商品主图
     */
    private String productImage;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 产品类别
     */
    private Integer cateType;
}
