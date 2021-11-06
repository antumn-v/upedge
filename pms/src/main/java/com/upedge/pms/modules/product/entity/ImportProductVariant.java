package com.upedge.pms.modules.product.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class ImportProductVariant {

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String sourceVariantId;
    /**
     *
     */
    private Long productId;
    /**
     * 已上传的变体所对应的新ID
     */
    private String platVariantId;
    /**
     * 已上传商品新生成的变体Id所对应的productId
     */
    private String platProductId;
    /**
     * 商品SKU
     */
    private String sku;
    /**
     *
     */
    private String image;
    /**
     * 产品成本
     */
    private BigDecimal cost;
    /**
     * 原价
     */
    private BigDecimal price;
    /**
     * 现价
     */
    private BigDecimal comparePrice;
    /**
     * 库存
     */
    private Long inventory;
    /**
     * 产品重量
     */
    private BigDecimal weight;
    /**
     *
     */
    private Integer state;



    public ImportProductVariant() {
    }
}
