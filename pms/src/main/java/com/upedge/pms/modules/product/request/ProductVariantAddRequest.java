package com.upedge.pms.modules.product.request;

import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.PriceUtils;
import com.upedge.pms.modules.product.entity.ProductVariant;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class ProductVariantAddRequest{

    /**
    * 商品id
    */
    @NotNull(message = "产品ID不能为空")
    private Long productId;
    /**
    * 变体图片
    */
    private String variantImage;
    /**
    * 变体价格
    */
    @NotNull(message = "产品价格不能为空")
    private BigDecimal variantPrice;
    /**
    * 重量
    */
    private BigDecimal weight;
    /**
    * 1:可用0:禁用
    */
    private Integer state;
    /**
    * 变体中文名
    */
    @NotNull(message = "变体中文名不能为空")
    private String cnName;
    /**
    * 变体英文名
    */
    @NotNull(message = "变体英文名不能为空")
    private String enName;


    public ProductVariant toProductVariant(){
        ProductVariant productVariant=new ProductVariant();
        productVariant.setId(IdGenerate.nextId());
        productVariant.setOriginalVariantId("0");
        productVariant.setProductId(productId);
        productVariant.setVariantSku(IdGenerate.nextId().toString());
        productVariant.setVariantImage(variantImage);
        productVariant.setVariantPrice(variantPrice);
        productVariant.setUsdPrice(PriceUtils.cnyToUsdByDefaultRate(variantPrice));
        productVariant.setInventory(9999L);
        productVariant.setWeight(weight);
        productVariant.setState(state);
        productVariant.setVolumeWeight(BigDecimal.ONE);
        productVariant.setVariantType(0);
        productVariant.setCnName(cnName);
        productVariant.setEnName(enName);
        productVariant.setLength(BigDecimal.ONE);
        productVariant.setWidth(BigDecimal.ONE);
        productVariant.setHeight(BigDecimal.ONE);
        return productVariant;
    }

}
