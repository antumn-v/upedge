package com.upedge.pms.modules.product.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 海桐
 */
@Data
public class StoreProductDto {
    private Long customerId;

    private Long orgId;

    private String title;

    private String source;

    private Integer relateState;

    private List<Long> orgIds;

}
