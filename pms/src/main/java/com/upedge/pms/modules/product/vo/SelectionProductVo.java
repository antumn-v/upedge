package com.upedge.pms.modules.product.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SelectionProductVo {
    /**
     *
     */
    private Long id;
    /**
     * 商品sku
     */
    private String productSku;
    /**
     * 商品标题
     */
    private String productTitle;
    /**
     * 商品主图
     */
    private String productImage;

    /**
     * 创建时间
     */
    private Date createTime;
}
