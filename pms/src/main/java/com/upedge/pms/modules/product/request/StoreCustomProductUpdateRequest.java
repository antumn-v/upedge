package com.upedge.pms.modules.product.request;


import lombok.Data;

/**
 * @author xwCui
 */
@Data
public class StoreCustomProductUpdateRequest{

    /**
     * 私有产品表id
     */
    private Long customerPrivateProductId;
    /**
     * 店铺id
     */
    private Long storeId;


}
