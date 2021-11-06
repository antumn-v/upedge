package com.upedge.pms.modules.product.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GroupVariantInfosVo {

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
     * 捆绑数量
     */
    private Integer quantity;
    /**
     * 变体名称
     */
    private String enName;
    /**
     * 变体价格
     */
    private BigDecimal variantPrice;
    /**
     * 重量
     */
    private BigDecimal weight;
    /**
     * 体积重
     */
    private BigDecimal volumeWeight;

}
