package com.upedge.pms.modules.product.vo;

import lombok.Data;

@Data
public class VariantVo {

    /**
     *
     */
    private Long id;
    /**
     * 商品id
     */
    private Long productId;
    /**
     * 变体sku
     */
    private String variantSku;
    /**
     * 变体图片
     */
    private String variantImage;
    /**
     *
     */
    private String cnName;
    /**
     *
     */
    private String enName;

}
