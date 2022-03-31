package com.upedge.pms.modules.product.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 海桐
 */
@Data
public class StoreProductDto {

    private Long id;

    private Long customerId;

    private String storeVariantSku;

    private String storeName;
    /**
     *0:未转换  1:已转换
     */
    private Integer transformState;

    private Long storeId;

    private Long orgId;

    private String title;

    private String source;

    private Integer relateState;

    private List<Long> orgIds;

    private List<Long> ids;

}
