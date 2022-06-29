package com.upedge.pms.modules.product.request;

import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.PriceUtils;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.entity.ProductVariantAttr;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
    @NotNull
    private BigDecimal weight;
    /**
    * 1:可用0:禁用
    */
    @NotNull
    private Integer state;
    /**
    * 变体中文名
    */
//    @NotNull(message = "变体中文名不能为空")
//    private String cnName;
//    /**
//    * 变体英文名
//    */
//    @NotNull(message = "变体英文名不能为空")
//    private String enName;

    @Size(min = 1,message = "变体属性不能为空")
    List<ProductVariantAttr> variantAttrs;


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
        productVariant.setVariantType(2);
        productVariant.setLength(BigDecimal.ONE);
        productVariant.setWidth(BigDecimal.ONE);
        productVariant.setHeight(BigDecimal.ONE);

        List<String> cnNameList = variantAttrs.stream().map(ProductVariantAttr::getVariantAttrCvalue).collect(Collectors.toList());
        List<String> enNameList = variantAttrs.stream().map(ProductVariantAttr::getVariantAttrEvalue).collect(Collectors.toList());
        productVariant.setCnName(cnNameList.toString());
        productVariant.setEnName(enNameList.toString());
        return productVariant;
    }

}
