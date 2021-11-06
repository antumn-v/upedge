package com.upedge.pms.modules.product.vo;

import lombok.Data;

/**
 * 客户信息 -- 私有产品列表
 */
@Data
public class PrivateProductVo {

    /**
     * 产品id
     */
    private String id;

    /**
     * 产品图片 product_image
     */
    private String productImage;

    /**
     * 产品名称 product_title
     */
    private String productTitle;
}
