package com.upedge.common.model.oms.order;

import lombok.Data;

@Data
public class OrderExcelItemDto {

    private String sku;

    private String image;

    private String sellingLink;

    private Integer quantity;
}
