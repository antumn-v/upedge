package com.upedge.oms.modules.order.vo;

import lombok.Data;

@Data
public class StoreOrderItemVo {

    private Long id;
    /**
     *
     */
    private Long storeVariantId;
    /**
     *
     */
    private Long storeProductId;
    /**
     *
     */
    private String storeVariantName;
    /**
     *
     */
    private String storeVariantSku;
    /**
     *
     */
    private String storeVariantImage;
    /**
     *
     */
    private String storeProductTitle;

}
