package com.upedge.pms.modules.product.vo;

import com.upedge.pms.modules.product.entity.ImportProductVariant;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AppProductVariantVo {

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
     * 变体价格
     */
    private BigDecimal variantPrice;

    private BigDecimal usdPrice;
    /**
     * 变体库存
     */
    private Long inventory;
    /**
     * 重量
     */
    private BigDecimal weight;

    private Integer state;

    /**
     * 体积重
     */
    private BigDecimal volumeWeight;

    private String enName;

    private List<AppProductVariantAttrVo> attrs;


    public ImportProductVariant toImportVariant(Long id){
        ImportProductVariant variant = new ImportProductVariant();
        variant.setId(id);
        variant.setState(1);
        variant.setImage(variantImage);
        variant.setSku(variantSku);
        variant.setSourceVariantId(String.valueOf(id));
        variant.setInventory(inventory);
        variant.setWeight(weight);
        return variant;
    }

}
