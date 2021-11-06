package com.upedge.pms.modules.quote.dto;

import lombok.Data;

@Data
public class QuoteApplyListDto {


    private Long id;
    /**
     *
     */
    private Long applyUserId;
    /**
     *
     */
    private Long customerId;
    /**
     *
     */
    private Long handleUserId;
    /**
     * 关联订单ID
     */
    private Long relateOrderId;
    /**
     * 0=待认领，1=处理中，2=已完成
     */
    private Integer quoteState;
    /**
     * 0=未报价，1=全部报价，2=部分报价
     */
    private Integer quoteType;
    /**
     * 店铺产品ID
     */
    private Integer storeProductId;
    /**
     * 店铺产品SKU
     */
    private String storeVariantSku;
}
