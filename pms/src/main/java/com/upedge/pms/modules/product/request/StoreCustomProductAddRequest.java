package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xwCui
 */
@Data
public class StoreCustomProductAddRequest{

    /**
    * 私有产品表id
    */
    @NotNull(message = "私有产品表id不能为null")
    private Long customerPrivateProductId;
    /**
    * 店铺id
    */
    @NotNull(message = "店铺id不能为null")
    private Long storeId;



}
