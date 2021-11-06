package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.ImportProductVariant;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class ImportProductVariantAddRequest{

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
            private Long storeVariantId;
                                /**
             * 已上传商品新生成的变体Id所对应的productId
             */
            private Long storeProductId;
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
            private Integer status;
            
    public ImportProductVariant toImportProductVariant(){
        ImportProductVariant importProductVariant=new ImportProductVariant();
        importProductVariant.setSourceVariantId(sourceVariantId);
        importProductVariant.setProductId(productId);
        importProductVariant.setPlatVariantId(String.valueOf(storeVariantId));
        importProductVariant.setPlatProductId(String.valueOf(storeProductId));
        importProductVariant.setSku(sku);
        importProductVariant.setImage(image);
        importProductVariant.setCost(cost);
        importProductVariant.setPrice(price);
        importProductVariant.setComparePrice(comparePrice);
        importProductVariant.setInventory(inventory);
        importProductVariant.setWeight(weight);
        importProductVariant.setState(status);
        return importProductVariant;
    }

}
