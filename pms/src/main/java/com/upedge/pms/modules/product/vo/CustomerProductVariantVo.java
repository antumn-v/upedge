package com.upedge.pms.modules.product.vo;

import lombok.Data;

@Data
public class CustomerProductVariantVo {

    /**
     * 变体ID
     */
    private Long id;
    /**
     * 店铺平台变体ID
     */
    private String platVariantId;
    /**
     * 店铺平台产品ID
     */
    private String platProductId;
    /**
     *
     */
    private String title;
    /**
     *
     */
    private String sku;
    /**
     * 图片
     */
    private String image;
}
