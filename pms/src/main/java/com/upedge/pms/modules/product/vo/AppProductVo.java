package com.upedge.pms.modules.product.vo;

import com.upedge.pms.modules.product.entity.Product;
import com.upedge.pms.modules.product.entity.ProductImg;
import com.upedge.pms.modules.product.entity.ProductInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class AppProductVo extends Product {

    private String turnover;

    private String score;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Integer importState;

    List<AppProductVariantVo> variantVos;

    List<VariantNameVo> variantNameValues;

    ProductInfo productInfo;

    List<ProductImg> productImgs;

    Map<String, Set<String>> attributeMap;


    public void initUsdPriceRange(){

        BigDecimal usdRate = new BigDecimal("6.3");
        if (this.minPrice != null) {
            this.minPrice = this.minPrice.divide(usdRate,2,BigDecimal.ROUND_UP);
        } else {
            this.minPrice = BigDecimal.ZERO;
        }
        if (this.maxPrice == null){
            this.maxPrice = BigDecimal.ZERO;
        }else {
            this.maxPrice = this.maxPrice.divide(usdRate,2,BigDecimal.ROUND_UP);
        }
        int i = this.minPrice.compareTo(this.maxPrice);
        switch (i){
            case 0:
                this.setPriceRange("$" + this.minPrice);
                break;
            case 1:
                this.setPriceRange("$" +this.maxPrice + "~$" + this.minPrice);
                break;
            case -1:
                this.setPriceRange("$" + this.minPrice + "~$" + this.maxPrice);
                break;
            default:
                this.setPriceRange("$" + this.minPrice);
                break;
        }


    }
}
