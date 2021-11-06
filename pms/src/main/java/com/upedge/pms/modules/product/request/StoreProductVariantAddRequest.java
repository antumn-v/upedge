package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.StoreProductVariant;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class StoreProductVariantAddRequest{

                                    /**
             * 变体对应的产品ID
             */
            private Long productId;
                                /**
             * 店铺平台变体ID
             */
            private String platVariantId;
                                /**
             * 店铺平台产品ID
             */
            private String platProductId;
                                /**
             * 
             */
            private String title;
                                /**
             * 售价
             */
            private BigDecimal price;
                                /**
             * 
             */
            private String sku;
                                /**
             * 图片
             */
            private String image;
                                /**
             * 
             */
            private Long adminVariantId;
                                /**
             * 1:可用 0不可用
             */
            private Integer state;
                                /**
             * 导入系统时间
             */
            private Date importTime;
            
    public StoreProductVariant toStoreProductVariant(){
        StoreProductVariant storeProductVariant=new StoreProductVariant();
        storeProductVariant.setProductId(productId);
        storeProductVariant.setPlatVariantId(platVariantId);
        storeProductVariant.setPlatProductId(platProductId);
        storeProductVariant.setTitle(title);
        storeProductVariant.setPrice(price);
        storeProductVariant.setSku(sku);
        storeProductVariant.setImage(image);
        storeProductVariant.setAdminVariantId(adminVariantId);
        storeProductVariant.setState(state);
        storeProductVariant.setImportTime(importTime);
        return storeProductVariant;
    }

}
