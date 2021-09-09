package com.upedge.pms.modules.product.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.ProductVariant;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class ProductVariantAddRequest{

    /**
    * 
    */
    private Long originalVariantId;
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
    /**
    * 
    */
    private BigDecimal usdPrice;
    /**
    * 变体库存
    */
    private Long inventory;
    /**
    * 重量
    */
    private BigDecimal weight;
    /**
    * 1:可用0:禁用
    */
    private Integer state;
    /**
    * 体积重
    */
    private BigDecimal volumeWeight;
    /**
    * 0:正常产品 1:捆绑产品
    */
    private Integer variantType;
    /**
    * 变体中文名
    */
    private String cnName;
    /**
    * 变体英文名
    */
    private String enName;
    /**
    * 长
    */
    private BigDecimal length;
    /**
    * 宽
    */
    private BigDecimal width;
    /**
    * 高
    */
    private BigDecimal height;

    public ProductVariant toProductVariant(){
        ProductVariant productVariant=new ProductVariant();
        productVariant.setOriginalVariantId(originalVariantId);
        productVariant.setProductId(productId);
        productVariant.setVariantSku(variantSku);
        productVariant.setVariantImage(variantImage);
        productVariant.setVariantPrice(variantPrice);
        productVariant.setUsdPrice(usdPrice);
        productVariant.setInventory(inventory);
        productVariant.setWeight(weight);
        productVariant.setState(state);
        productVariant.setVolumeWeight(volumeWeight);
        productVariant.setVariantType(variantType);
        productVariant.setCnName(cnName);
        productVariant.setEnName(enName);
        productVariant.setLength(length);
        productVariant.setWidth(width);
        productVariant.setHeight(height);
        return productVariant;
    }

}
