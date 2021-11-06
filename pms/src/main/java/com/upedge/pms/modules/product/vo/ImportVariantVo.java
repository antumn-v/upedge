package com.upedge.pms.modules.product.vo;

import com.upedge.pms.modules.product.entity.ImportProductVariantAttr;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 海桐
 */
@Data
public class ImportVariantVo {

    private Long id;
    /**
     *
     */
    private Long productId;

    private String sourceVariantId;

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

    private BigDecimal shipPrice;
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

    private List<ImportProductVariantAttr> attrs;

}
