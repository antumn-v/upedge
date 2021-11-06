package com.upedge.pms.modules.product.vo;

import lombok.Data;

@Data
public class StoreCustomerPrivateProductVo {

    /**
     *
     */
    private Long id;
    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 用户ID
     */
    private Long customerId;

    private String email;

    private String customerName;

    /**
     *
     */
    private Long StoreCustomProductId;

    /**
     * 店铺id
     */
    private Long storeId;
}
