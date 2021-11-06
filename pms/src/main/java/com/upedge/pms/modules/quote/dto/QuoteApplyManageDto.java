package com.upedge.pms.modules.quote.dto;

public class QuoteApplyManageDto {

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
     *
     */
    private Long relateOrderId;
    /**
     * 0=待认领，1=处理中，2=已完成
     */
    private Integer quoteState = 1;
    /**
     * 0=未报价，1=全部报价，2=部分报价
     */
    private Integer quoteType;

    private Integer storeProductId;

    private String storeProductSku;
}
