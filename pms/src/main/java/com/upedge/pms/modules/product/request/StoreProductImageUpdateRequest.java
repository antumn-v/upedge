package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.StoreProductImage;
import lombok.Data;

/**
 * @author author
 */
@Data
public class StoreProductImageUpdateRequest{

    /**
     * 
     */
    private String platImageId;
    /**
     * 
     */
    private String platProductId;
    /**
     * 对应店铺产品ID
     */
    private Long productId;
    /**
     * 图片SRC
     */
    private String src;

    public StoreProductImage toStoreProductImage(Long id){
        StoreProductImage storeProductImage=new StoreProductImage();
        storeProductImage.setId(id);
        storeProductImage.setPlatImageId(platImageId);
        storeProductImage.setPlatProductId(platProductId);
        storeProductImage.setProductId(productId);
        storeProductImage.setSrc(src);
        return storeProductImage;
    }

}
